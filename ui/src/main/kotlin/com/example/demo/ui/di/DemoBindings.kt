package com.example.demo.ui.di

import com.example.demo.ui.MainActivity
import com.example.demo.ui.fragments.home.HomeFragment
import com.squareup.anvil.annotations.ContributesTo
import javax.inject.Singleton

@ContributesTo(Singleton::class)
interface DemoBindings {
    fun inject(mainActivity: MainActivity)

    fun inject(homeFragment: HomeFragment)
}
