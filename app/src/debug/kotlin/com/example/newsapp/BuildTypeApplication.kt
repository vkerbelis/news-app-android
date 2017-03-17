package com.example.newsapp

import android.app.Application
import android.view.View

import com.facebook.stetho.Stetho
import com.facebook.stetho.timber.StethoTree
import com.squareup.leakcanary.LeakCanary
import com.squareup.leakcanary.RefWatcher

import timber.log.Timber

open class BuildTypeApplication : Application() {
    private lateinit var refWatcher: RefWatcher

    override fun onCreate() {
        super.onCreate()
        Stetho.initialize(Stetho.newInitializerBuilder(this)
                .enableDumpapp(Stetho.defaultDumperPluginsProvider(this))
                .enableWebKitInspector(Stetho.defaultInspectorModulesProvider(this))
                .build())
        Timber.plant(Timber.DebugTree())
        Timber.plant(StethoTree())

        refWatcher = LeakCanary.install(this)
    }

    companion object {
        fun watchView(view: View) {
            (view.context.applicationContext as BuildTypeApplication).refWatcher.watch(view)
            Timber.d("LeakCanary watching object reference: %s", view)
        }
    }
}
