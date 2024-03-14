package com.example.demo.ui.di

import android.content.Context
import android.content.ContextWrapper
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

// region Public API

/**
 * Use this to create a dagger component scoped to the full lifecycle of a Fragment. The component is created lazily and cleared when the
 * Fragment is destroyed.
 */
fun <T : Any> Fragment.daggerSubcomponent(factory: () -> T): ReadOnlyProperty<Fragment, T> = FragmentComponentHolderDelegate(this, factory)

/** @see [Fragment.singletonBindings] */
inline fun <reified T : Any> Context.singletonBindings() =
    sequenceOf(applicationContext).findComponent<T>()
        ?: run {
            val bindingName = T::class.java.simpleName
            error(
                "Unable to find bindings for $bindingName in the ApplicationComponent. If you're using a dagger.Subcomponent, you probably need `scopedBindings<$bindingName>()`."
            )
        }

/** @see [Fragment.singletonBindings] */
inline fun <reified T : Any> View.singletonBindings() = context.singletonBindings<T>()

/**
 * Get the Dagger "Bindings" from the ApplicationComponent. Bindings must an interface that uses `@ContributesTo(Singleton::class)`. They
 * are used if you need to directly interact with a dagger component such as:
 * * an inject function: `inject(MyFragment frag)`
 * * an explicit getter: `fun myClass(): MyClass`
 */
inline fun <reified T : Any> Fragment.singletonBindings() = requireContext().singletonBindings<T>()

/** @see [Fragment.scopedBindings] */
inline fun <reified T : Any> Context.scopedBindings(): T {
    return generateSequence(this) { (it as? ContextWrapper)?.baseContext }
        .plus(applicationContext)
        .findComponent<T>()
        .also {
            Log.d(
                "DaggerBindings",
                "Binding ${T::class.java.simpleName}${if (it == null) " not" else ""} found in the Context hierarchy${if (it == null) "." else ": ${it.javaClass.simpleName}"}"
            )
        } ?: error("Unable to find bindings for ${T::class.java.simpleName} in the Context hierarchy.")
}

/** @see [Fragment.scopedBindings] */
inline fun <reified T : Any> View.scopedBindings() = context.scopedBindings<T>()

/**
 * Get the Dagger "Bindings" from the Fragment hierarchy If the binding is not found there, search in the Context hierarchy. Bindings must
 * be a Dagger Component or an interface that uses `@ContributesTo(SomeScope::class)`. They are used if you need to directly interact with a
 * dagger component such as:
 * * an inject function: `inject(MyFragment frag)`
 * * an explicit getter: `fun myClass(): MyClass`
 */
inline fun <reified T : Any> Fragment.scopedBindings(): T {
    return rootFragment().childFragmentManager.fragments.asSequence().findComponent<T>()?.also {
        Log.d("DaggerBindings", "Binding ${T::class.java.simpleName} found in the Fragment hierarchy: ${it.javaClass.simpleName}.")
    }
        ?: run {
            Log.d(
                "DaggerBindings",
                "Binding ${T::class.java.simpleName} not found in the Fragment hierarchy, trying in the Context hierarchy."
            )
            requireActivity().scopedBindings()
        }
}

// endregion

// region Deprecated

@Deprecated("Use singletonBindings() instead.", ReplaceWith("singletonBindings<T>()", "com.santander.one.presentation.di"))
inline fun <reified T : Any> Fragment.bindings() = singletonBindings<T>()

@Deprecated("Use singletonBindings() instead.", ReplaceWith("singletonBindings<T>()", "com.santander.one.presentation.di"))
inline fun <reified T : Any> Context.bindings() = singletonBindings<T>()

@Deprecated("Use singletonBindings() instead.", ReplaceWith("singletonBindings<T>()", "com.santander.one.presentation.di"))
inline fun <reified T : Any> View.bindings() = singletonBindings<T>()

// endregion

// region Internals

@PublishedApi
internal inline fun <reified T : Any> Sequence<*>.findComponent() = firstNotNullOfOrNull {
    val component = (it as? DaggerComponentProvider<*>)?.daggerComponent
    if (component is T) component else null
}

@PublishedApi internal tailrec fun Fragment.rootFragment(): Fragment = if (parentFragment == null) this else parentFragment!!.rootFragment()

@PublishedApi
internal class FragmentComponentHolderDelegate<T : Any>(
    fragment: Fragment,
    private val factory: () -> T,
) : ReadOnlyProperty<Fragment, T>, LifecycleEventObserver {
    private var component: T? = null

    init {
        fragment.lifecycle.addObserver(this)
    }

    @Synchronized override fun getValue(thisRef: Fragment, property: KProperty<*>) = component ?: initialize()

    override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
        if (event == Lifecycle.Event.ON_DESTROY) {
            Log.d("DaggerBindings", "Removing subcomponent ${component?.javaClass?.simpleName}.")
            component = null
        }
    }

    @Synchronized
    private fun initialize() =
        component
            ?: factory().also {
                Log.d("DaggerBindings", "Created subcomponent ${it.javaClass.simpleName}.")
                component = it
            }
}

// endregion
