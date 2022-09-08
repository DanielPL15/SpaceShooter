package com.danielpl.spaceshootersample.di

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.danielpl.spaceshootersample.preferences.DefaultPreferences
import com.danielpl.spaceshootersample.preferences.Preferences
import com.danielpl.spaceshootersample.util.Config
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideSharedPreferences(
        app: Application
    ): SharedPreferences {
        return app.getSharedPreferences("shared_pref", Context.MODE_PRIVATE)
    }

    @Provides
    @Singleton
    fun providePreferences(sharedPreferences: SharedPreferences): Preferences {
        return DefaultPreferences(sharedPreferences)
    }

    @Provides
    @Singleton
    fun provideConfig(): Config{
        return Config()
    }


}