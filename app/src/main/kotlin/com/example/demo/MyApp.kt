package com.example.demo

import android.app.Application
import com.example.demo.di.component.AppComponents
import com.example.demo.di.component.DaggerAppComponents
import com.example.demo.di.provider.ApplicationComponentProvider

class MyApp : Application(), ApplicationComponentProvider {


    val appComponents: AppComponents by lazy {
        getAppComponents()
    }

    override fun onCreate() {
        super.onCreate()
        appComponents.inject(this)
//        InternetUtil.init(this)
    }

    override fun getAppComponents(): AppComponents {
        return DaggerAppComponents
            .factory()
            .create(this)
    }

}