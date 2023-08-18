package com.vimal.mealapp.api

import android.content.Context
import androidx.room.Room
import com.vimal.mealapp.database.AppDatabase
import com.vimal.mealapp.database.FavouriteMealCategoryDao
import com.vimal.mealapp.database.MealDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Singleton
    @Provides
    fun providesRetrofit(): Retrofit {
        return Retrofit.Builder().baseUrl("https://www.themealdb.com/api/json/v1/1/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Singleton
    @Provides
    fun providesAPI(retrofit: Retrofit): RetrofitService {
        return retrofit.create(RetrofitService::class.java)
    }

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            AppDatabase::class.java,
            "app_database"
        ).build()
    }

    @Singleton
    @Provides
    fun providesCharacterDao(database: AppDatabase): FavouriteMealCategoryDao {
        return database.getCharacterDao()
    }

    @Singleton
    @Provides
    fun providesMealDao(database: AppDatabase): MealDao {
        return database.getMealDao()
    }

}