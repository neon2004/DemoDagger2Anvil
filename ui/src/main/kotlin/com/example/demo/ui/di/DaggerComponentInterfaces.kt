package com.example.demo.ui.di

import androidx.fragment.app.Fragment

interface DaggerComponentProvider<T> {
    val daggerComponent: T
}

interface DaggerComponentChild<T> : DaggerComponentProvider<T> {

    val topFragment: Fragment

    override val daggerComponent: T
        get() = (topFragment as DaggerComponentProvider<T>).daggerComponent
}
