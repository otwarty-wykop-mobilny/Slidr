package io.github.usefulness.slidr.example

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import io.github.usefulness.slidr.example.databinding.LayoutItemBinding
import io.github.usefulness.slidr.example.model.AndroidOs

internal class OSVersionAdapter(private val onClick: (AndroidOs) -> Unit) :
    ListAdapter<AndroidOs, OSVersionAdapter.BindingViewHolder>(AndroidOsDiff) {

    data class BindingViewHolder(val binding: LayoutItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        BindingViewHolder(LayoutItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: BindingViewHolder, position: Int) {
        val os = getItem(position)
        holder.binding.title.text = os.name
        holder.binding.description.text = os.description
        holder.binding.root.setOnClickListener { onClick(os) }
    }
}

internal object AndroidOsDiff : DiffUtil.ItemCallback<AndroidOs>() {
    override fun areItemsTheSame(oldItem: AndroidOs, newItem: AndroidOs) = oldItem.name == newItem.name

    override fun areContentsTheSame(oldItem: AndroidOs, newItem: AndroidOs) = oldItem == newItem
}
