package com.example.demo.di.component

import com.example.demo.MyApp
import com.squareup.anvil.annotations.MergeComponent
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton



@Singleton
@MergeComponent(
    scope = Singleton::class,
//    modules = [NetworkModule::class],
)
interface AppComponents {
    @Component.Factory
    interface Factory {
        fun create(
            @BindsInstance app: MyApp,
        ): AppComponents
    }

    fun inject(myApp: MyApp)


}