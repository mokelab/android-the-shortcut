package com.mokelab.tools.shortcut.pages.create.setting

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.LiveData
import androidx.navigation.fragment.findNavController
import com.google.android.material.textfield.TextInputLayout
import com.mokelab.tools.shortcut.R
import com.mokelab.tools.shortcut.databinding.CreateSettingsShortcutFragmentBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CreateSettingShortcutFragment : Fragment(R.layout.create_settings_shortcut_fragment) {
    private var _binding: CreateSettingsShortcutFragmentBinding? = null
    private val binding get() = _binding!!

    private val vm: CreateSettingShortcutViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        this._binding = CreateSettingsShortcutFragmentBinding.bind(view)

        observeError(this.vm.idError, binding.layoutId)
        observeError(this.vm.labelError, binding.layoutLabel)
        this.vm.done.observe(viewLifecycleOwner, {
            findNavController().popBackStack()
        })

        binding.submitButton.setOnClickListener {
            val id = binding.idEdit.text.toString()
            val label = binding.labelEdit.text.toString()

            vm.create(id, label)
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