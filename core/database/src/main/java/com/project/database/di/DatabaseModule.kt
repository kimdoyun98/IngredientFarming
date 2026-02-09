package com.project.database.di

import android.content.Context
import androidx.room.Room
import com.project.database.IngredientFarmingDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object DatabaseModule {

    @Singleton
    @Provides
    fun provideDatabase(
        @ApplicationContext context: Context,
    ): IngredientFarmingDatabase = Room.databaseBuilder(
        context,
        IngredientFarmingDatabase::class.java,
        "IngredientFarmingDatabase"
    ).build()
}
