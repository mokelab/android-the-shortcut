package com.mokelab.tools.shortcut.pages.create.setting

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.VectorDrawable
import android.provider.Settings
import androidx.core.content.ContextCompat
import androidx.core.content.pm.ShortcutInfoCompat
import androidx.core.content.pm.ShortcutManagerCompat
import androidx.core.graphics.drawable.IconCompat
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mokelab.tools.shortcut.R
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.launch

class CreateSettingShortcutViewModel @ViewModelInject constructor(
    @ApplicationContext private val context: Context
) :
    ViewModel() {
    val idError = MutableLiveData<String>()
    val labelError = MutableLiveData<String>()
    val done = MutableLiveData<Boolean>()

    fun create(
        id: String,
        label: String
    ) {
        viewModelScope.launch {
            if (!isValidInput(id, label)) {
                return@launch
            }
            val info = ShortcutInfoCompat.Builder(context, id).apply {
                val intent = Intent(Settings.ACTION_APPLICATION_DEVELOPMENT_SETTINGS)

                setShortLabel(label)
                setIntent(intent)
                setIcon(IconCompat.createWithAdaptiveBitmap(createIcon()))
            }.build()

            ShortcutManagerCompat.addDynamicShortcuts(context, listOf(info))
            done.value = true
        }
    }

    private fun isValidInput(
        id: String,
        label: String
    ): Boolean {
        var hasError = false
        if (id.trim().isEmpty()) {
            idError.value = "Input ID"
            hasError = true
        } else {
            idError.value = ""
        }
        if (label.trim().isEmpty()) {
            labelError.value = "Input label"
            hasError = true
        } else {
            labelError.value = ""
        }
        return !hasError
    }

    fun createIcon(): Bitmap {
        val drawable =
            ContextCompat.getDrawable(context, R.drawable.ic_baseline_settings_24) as VectorDrawable
        val bitmap = Bitmap.createBitmap(
            drawable.intrinsicWidth,
            drawable.intrinsicHeight,
            Bitmap.Config.ARGB_8888
        )
        val canvas = Canvas(bitmap)
        drawable.setBounds(16, 16, canvas.width - 16, canvas.height - 16)
        drawable.draw(canvas)
        return bitmap
    }
}