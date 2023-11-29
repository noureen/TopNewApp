package com.example.topnewapp.di

import android.content.Context
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.example.topnewapp.R
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun provideRequestOptions() = RequestOptions()
        .placeholder(R.drawable.ic_launcher_background)
        .error(android.R.drawable.stat_notify_error)
        .diskCacheStrategy(DiskCacheStrategy.DATA) // Cache only the original full-resolution image


    @Provides
    fun provideGlideInstance(
        @ApplicationContext context: Context,
        requestOptions: RequestOptions
    ) = Glide.with(context)
        .setDefaultRequestOptions(requestOptions)

}