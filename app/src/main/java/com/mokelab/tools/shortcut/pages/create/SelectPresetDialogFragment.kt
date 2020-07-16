package com.mokelab.tools.shortcut.pages.create

import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.setFragmentResult
import com.mokelab.tools.shortcut.model.preset.PresetShortcut
import com.mokelab.tools.shortcut.model.preset.PresetShortcutRepository
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SelectPresetDialogFragment : DialogFragment() {
    private lateinit var items: List<PresetShortcut>

    @Inject
    lateinit var repo: PresetShortcutRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.items = repo.getAll()
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val activity = this.activity ?: return super.onCreateDialog(savedInstanceState)

        return AlertDialog.Builder(activity).apply {
            val labels = items.map { it.label }.toTypedArray()
            setItems(labels, listener)
        }.create()
    }

    private val listener = DialogInterface.OnClickListener { _, position ->
        val item = this.items[position]
        setFragmentResult(REQUEST_KEY, bundleOf(Pair<String, Any?>(KEY_ITEM, item)))
    }

    companion object {
        const val REQUEST_KEY = "shortcut"
        const val KEY_ITEM = "item"
    }
}