package com.technokratos.compose.localization.app

import android.app.Application

class SampleApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        Class.forName("com.technokratos.compose.localization.locales.StringsKt")
        Class.forName("com.technokratos.compose.localization.locales_override.Strings")
        Class.forName("com.technokratos.compose.localization.app.ui.Strings")
    }
    
}