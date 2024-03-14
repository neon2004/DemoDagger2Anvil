package com.example.demo.data.di

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import com.squareup.anvil.annotations.ContributesBinding
import javax.inject.Inject
import javax.inject.Singleton

//@Singleton
//@ContributesBinding(Singleton::class)
//class NetworkManager @Inject constructor(private val context: Context){
//
//    fun checkConnectivity() = isInternetAvailable(context)
//
////    companion object {
//        /** Checks if there is any active network */
//        private fun isInternetAvailable(context: Context): Boolean {
//            val activeNetwork = getActiveNetwork(context)
//            return activeNetwork != null && activeNetwork.isConnectedOrConnecting
//        }
//
//        /** Get the active [NetworkInfo] */
//        private fun getActiveNetwork(context: Context): NetworkInfo? {
//            return (context.getSystemService(Context.CONNECTIVITY_SERVICE) as? ConnectivityManager)?.activeNetworkInfo
//        }
////    }
//}
