package com.example.demo

import android.app.Application
import com.example.demo.di.component.AppComponents
import com.example.demo.di.component.DaggerAppComponents
import com.example.demo.di.modules.InternetUtil

class MyApp : Application() {

    val appComponents: AppComponents by lazy {
        DaggerAppComponents
            .factory()
            .create(this)
    }

    override fun onCreate() {
        super.onCreate()
        InternetUtil.init(this)
    }

}