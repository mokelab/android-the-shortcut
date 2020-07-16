package com.mokelab.tools.shortcut.pages.main

import android.content.Context
import androidx.core.content.pm.ShortcutInfoCompat
import androidx.core.content.pm.ShortcutManagerCompat
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import dagger.hilt.android.qualifiers.ApplicationContext

class MainViewModel @ViewModelInject constructor(
    @ApplicationContext private val context: Context
) : ViewModel() {
    val shortCuts = MutableLiveData<List<ShortcutInfoCompat>>()

    fun fetch() {
        shortCuts.value = ShortcutManagerCompat.getDynamicShortcuts(context)
    }


}