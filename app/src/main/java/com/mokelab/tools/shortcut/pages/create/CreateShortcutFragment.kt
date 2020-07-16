package com.mokelab.tools.shortcut.pages.create

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.LiveData
import androidx.navigation.fragment.findNavController
import com.google.android.material.textfield.TextInputLayout
import com.mokelab.tools.shortcut.R
import com.mokelab.tools.shortcut.databinding.CreateShortcutFragmentBinding
import com.mokelab.tools.shortcut.model.preset.PresetShortcut
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CreateShortcutFragment : Fragment(R.layout.create_shortcut_fragment) {
    private var _binding: CreateShortcutFragmentBinding? = null
    private val binding get() = _binding!!

    private val vm: CreateShortcutViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setFragmentResultListener(SelectPresetDialogFragment.REQUEST_KEY) { _, data ->
            val shortcut = data[SelectPresetDialogFragment.KEY_ITEM] as? PresetShortcut
                ?: return@setFragmentResultListener
            binding.packageNameEdit.setText(shortcut.packageName)
            binding.componentNameEdit.setText(shortcut.componentName)
            binding.actionEdit.setText(shortcut.action)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        this._binding = CreateShortcutFragmentBinding.bind(view)

        observeError(this.vm.idError, binding.layoutId)
        observeError(this.vm.labelError, binding.layoutLabel)
        observeError(this.vm.packageNameError, binding.layoutPackageName)
        observeError(this.vm.componentNameError, binding.layoutComponentName)
        observeError(this.vm.actionError, binding.layoutAction)
        this.vm.done.observe(viewLifecycleOwner, {
            findNavController().popBackStack()
        })

        binding.selectPresetButton.setOnClickListener {
            findNavController().navigate(R.id.action_create_to_dialog)
        }

        binding.settingsButton.setOnClickListener {
            findNavController().navigate(R.id.action_create_to_setting)
        }

        binding.submitButton.setOnClickListener {
            val id = binding.idEdit.text.toString()
            val label = binding.labelEdit.text.toString()
            val packageName = binding.packageNameEdit.text.toString()
            val componentName = binding.componentNameEdit.text.toString()
            val action = binding.actionEdit.text.toString()

            vm.create(id, label, packageName, componentName, action)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        this._binding = null
    }

    private fun observeError(liveData: LiveData<String>, layout: TextInputLayout) {
        liveData.observe(viewLifecycleOwner, {
            layout.error = it
        })
    }
}