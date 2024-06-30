package com.argon.launcher.presentation.activity.launcher.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.argon.launcher.BR
import com.argon.launcher.data.model.AppUiModel
import com.argon.launcher.databinding.ItemAppBinding
import com.argon.launcher.util.widget.edgefactory.BaseViewHolder

class AppsAdapter(private val list: MutableList<AppUiModel>): RecyclerView.Adapter<AppsAdapter.AppViewHolder>() {

    class AppViewHolder(binding: ViewDataBinding) : BaseViewHolder(binding.root) {
        var binding: ViewDataBinding
            internal set

        init {
            this.binding = binding
            this.binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AppViewHolder {
        val binding = ItemAppBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return AppViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: AppViewHolder, position: Int) {
        holder.binding.setVariable(BR.position, position)
        holder.binding.setVariable(BR.item, getItemData(position))
    }

    private fun getItemData(position: Int) = list[position]

    private fun getPosition(item: AppUiModel) = list.indexOf(item)

}