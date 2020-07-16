package com.mokelab.tools.shortcut.model.preset

import android.content.Context
import com.mokelab.tools.shortcut.R
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class PresetShortcutRepositoryImpl @Inject constructor(
    @ApplicationContext private val context: Context
) :
    PresetShortcutRepository {
    override fun getAll(): List<PresetShortcut> {
        return listOf(
            PresetShortcut(
                context.getString(R.string.preset_system_ui_debug),
                "com.android.systemui", "com.android.systemui.DemoMode",
                "com.android.settings.action.DEMO_MODE"
            )
        )
    }
}