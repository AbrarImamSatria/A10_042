package com.example.perkebunan

import android.app.Application
import com.example.perkebunan.dependenciesinjection.AppContainer
import com.example.perkebunan.dependenciesinjection.PerkebunanContainer

class PerkebunanApplications:Application() {
    lateinit var container: AppContainer
    override fun onCreate() {
        super.onCreate()
        container = PerkebunanContainer()
    }
}