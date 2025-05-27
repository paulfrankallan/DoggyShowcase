package com.paulallan.dogs.app

import android.app.Application
import com.paulallan.dogs.core.image.di.imageLoaderModule
import com.paulallan.dogs.core.network.di.networkModule
import com.paulallan.dogs.feature.breedgallery.di.breedGalleryModule
import com.paulallan.dogs.feature.breedlist.di.breedListModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.startKoin

class App: Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@App)
            modules(
                networkModule,
                breedListModule,
                breedGalleryModule,
                imageLoaderModule
            )
        }
    }
}
