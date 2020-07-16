package com.mokelab.tools.shortcut.pages.create

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.VectorDrawable
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

class CreateShortcutViewModel @ViewModelInject constructor(
    @ApplicationContext private val context: Context
) :
    ViewModel() {
    val idError = MutableLiveData<String>()
    val labelError = MutableLiveData<String>()
    val packageNameError = MutableLiveData<String>()
    val componentNameError = MutableLiveData<String>()
    val actionError = MutableLiveData<String>()
    val done = MutableLiveData<Boolean>()

    fun create(
        id: String,
        label: String,
        packageName: String,
        componentName: String,
        action: String
    ) {
        viewModelScope.launch {
            if (!isValidInput(id, label, packageName, componentName, action)) {
                return@launch
            }
            val info = ShortcutInfoCompat.Builder(context, id).apply {
                val intent = Intent(action)
                intent.setClassName(packageName, componentName)

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
        label: String,
        packageName: String,
        componentName: String,
        action: String
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
        if (packageName.trim().isEmpty()) {
            packageNameError.value = "Input package name"
            hasError = true
        } else {
            packageNameError.value = ""
        }
        if (componentName.trim().isEmpty()) {
            componentNameError.value = "Input component name"
            hasError = true
        } else {
            componentNameError.value = ""
        }
        if (action.trim().isEmpty()) {
            actionError.value = "Input action"
            hasError = true
        } else {
            actionError.value = ""
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