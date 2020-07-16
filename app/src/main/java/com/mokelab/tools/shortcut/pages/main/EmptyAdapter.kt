package com.mokelab.tools.shortcut.pages.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mokelab.tools.shortcut.R

class EmptyAdapter : RecyclerView.Adapter<EmptyAdapter.ViewHolder>() {
    private var state = 0

    fun setHasResult(hasResult: Boolean) {
        state = if (hasResult) 2 else 1
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ViewHolder(
            inflater.inflate(
                when (viewType) {
                    0 -> R.layout.loading_item
                    1 -> R.layout.empty_shortcut_item
                    else -> R.layout.loading_item
                }, parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

    }

    override fun getItemCount(): Int {
        return if (state == 2) {
            0
        } else {
            1
        }
    }

    override fun getItemViewType(position: Int) = state

    class ViewHolder(root: View) : RecyclerView.ViewHolder(root) {

    }

}