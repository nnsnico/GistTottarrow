package io.nns.tottarrow

import android.app.Application
import io.nns.tottarrow.di.appModule
import io.nns.tottarrow.di.networkModule
import io.nns.tottarrow.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class MainApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@MainApplication)
            modules(
                listOf(
                    appModule,
                    networkModule,
                    viewModelModule
                )
            )
        }
    }
}