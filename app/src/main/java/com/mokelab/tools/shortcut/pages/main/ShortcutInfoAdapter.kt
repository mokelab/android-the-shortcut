package com.mokelab.tools.shortcut.pages.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.pm.ShortcutInfoCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mokelab.tools.shortcut.databinding.InfoItemBinding

class ShortcutInfoAdapter : ListAdapter<ShortcutInfoCompat, ShortcutInfoAdapter.ViewHolder>(
    callbacks
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = InfoItemBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val info = getItem(position)
        holder.bindTo(info)
    }


    class ViewHolder(private val binding: InfoItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bindTo(info: ShortcutInfoCompat) {
            binding.shortLabelText.text = info.shortLabel
        }
    }

    companion object {
        private val callbacks = object : DiffUtil.ItemCallback<ShortcutInfoCompat>() {
            override fun areItemsTheSame(
                oldItem: ShortcutInfoCompat,
                newItem: ShortcutInfoCompat
            ) =
                oldItem.id == newItem.id

            override fun areContentsTheSame(
                oldItem: ShortcutInfoCompat,
                newItem: ShortcutInfoCompat
            ) =
                oldItem.id == newItem.id && oldItem.shortLabel.toString() == newItem.shortLabel.toString()
        }
    }

}