package com.example.demo.di.provider

import com.example.demo.di.component.AppComponents

interface ApplicationComponentProvider {

    fun getAppComponents(): AppComponents

}