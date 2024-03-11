package com.example.demo.di.modules

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import com.squareup.anvil.annotations.ContributesTo
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
@ContributesTo(Singleton::class)
object InternetUtil {

    private lateinit var application: Application
    fun init(application: Application) {
        InternetUtil.application = application
    }

    @Provides
    @Singleton
    fun isInternetOn(): Boolean {
        val cm = application.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork = cm.activeNetworkInfo
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting
    }

}