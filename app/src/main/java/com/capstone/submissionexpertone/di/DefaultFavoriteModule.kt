package com.capstone.submissionexpertone.di

import androidx.fragment.app.Fragment
import com.capstone.submissionexpertone.core.di.FavoriteModuleInterface
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DefaultFavoriteModule {

    @Provides
    @Singleton
    fun provideFavoriteModule(provider: FavoriteFragmentProvider): FavoriteModuleInterface {
        return object : FavoriteModuleInterface {
            override fun getFavoriteFragment(): Fragment {
                return provider.getFavoriteFragment()
            }
        }
    }
}