package com.mokelab.tools.shortcut.pages.main

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ConcatAdapter
import com.mokelab.tools.shortcut.R
import com.mokelab.tools.shortcut.databinding.MainFragmentBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainFragment : Fragment(R.layout.main_fragment) {
    private var _binding: MainFragmentBinding? = null
    private val binding get() = _binding!!

    private val vm: MainViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        this._binding = MainFragmentBinding.bind(view)

        val emptyAdapter = EmptyAdapter()
        val adapter = ShortcutInfoAdapter()
        binding.recycler.adapter = ConcatAdapter(emptyAdapter, adapter)

        vm.shortCuts.observe(viewLifecycleOwner, {
            emptyAdapter.setHasResult(it.isNotEmpty())
            adapter.submitList(it)
        })

        binding.fab.setOnClickListener {
            findNavController().navigate(R.id.action_main_to_create)
        }
    }

    override fun onStart() {
        super.onStart()
        this.vm.fetch()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        this._binding = null
    }
}