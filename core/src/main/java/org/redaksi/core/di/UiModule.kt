package org.redaksi.core.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import org.redaksi.core.helper.verse.VerseFinder


@Module
@InstallIn(ViewModelComponent::class)
object UiModule {

    @ViewModelScoped
    @Provides
    fun provideVerseFinder(): VerseFinder {
        return VerseFinder()
    }
}
