package com.stbn.quickrecipes.features.recipes.di

import com.stbn.quickrecipes.features.recipes.data.RecipeRepositoryImpl
import com.stbn.quickrecipes.features.recipes.domain.RecipeRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RecipeModule {
    @Binds
    @Singleton
    abstract fun bindRecipeRepository(
        impl: RecipeRepositoryImpl
    ): RecipeRepository
}