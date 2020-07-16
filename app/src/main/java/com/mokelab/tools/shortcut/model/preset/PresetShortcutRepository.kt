package com.mokelab.tools.shortcut.model.preset

interface PresetShortcutRepository {
    fun getAll(): List<PresetShortcut>
}