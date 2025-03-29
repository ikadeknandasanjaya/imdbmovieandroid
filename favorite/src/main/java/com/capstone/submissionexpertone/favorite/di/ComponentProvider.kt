package com.capstone.submissionexpertone.favorite.di

import android.content.Context
import com.capstone.submissionexpertone.di.FavoriteModuleDependencies
import com.capstone.submissionexpertone.favorite.FavoriteFragment
import dagger.BindsInstance
import dagger.Component
import dagger.hilt.android.EntryPointAccessors

@Component(dependencies = [FavoriteModuleDependencies::class])
interface FavoriteComponent {

    fun inject(fragment: FavoriteFragment)

    @Component.Builder
    interface Builder {
        fun context(@BindsInstance context: Context): Builder
        fun appDependencies(dependencies: FavoriteModuleDependencies): Builder
        fun build(): FavoriteComponent
    }

    companion object {
        fun create(context: Context): FavoriteComponent {
            val appDependencies = EntryPointAccessors.fromApplication(
                context.applicationContext,
                FavoriteModuleDependencies::class.java
            )
            return DaggerFavoriteComponent.builder()
                .context(context)
                .appDependencies(appDependencies)
                .build()
        }
    }
}