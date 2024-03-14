package com.example.demo.ui.di.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.squareup.anvil.annotations.ContributesBinding
import dagger.MapKey
import javax.inject.Inject
import javax.inject.Provider
import javax.inject.Singleton
import kotlin.reflect.KClass

@Target(
    AnnotationTarget.CLASS,
    AnnotationTarget.FUNCTION,
    AnnotationTarget.PROPERTY_GETTER,
    AnnotationTarget.PROPERTY_SETTER
)
@kotlin.annotation.Retention(AnnotationRetention.RUNTIME)
@MapKey
annotation class ViewModelKey(val value: KClass<out ViewModel>)

@Singleton
@ContributesBinding(Singleton::class)
class ViewModelFactory
@Inject
constructor(
    private val viewModels: MutableMap<Class<out ViewModel>, @JvmSuppressWildcards Provider<ViewModel>>,
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        viewModels[modelClass]?.get() as? T ?: error("ViewModel binding not found for class: $modelClass")
}
