package com.mokelab.tools.shortcut

import com.mokelab.tools.shortcut.model.preset.PresetShortcutRepository
import com.mokelab.tools.shortcut.model.preset.PresetShortcutRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
abstract class MyModule {
    @Binds
    @Singleton
    abstract fun providePresetShortcutRepository(repo: PresetShortcutRepositoryImpl): PresetShortcutRepository
}