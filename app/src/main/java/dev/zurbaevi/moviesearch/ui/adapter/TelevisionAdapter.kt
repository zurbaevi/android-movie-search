package dev.zurbaevi.moviesearch.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import dev.zurbaevi.moviesearch.R
import dev.zurbaevi.moviesearch.data.model.Television
import dev.zurbaevi.moviesearch.databinding.ItemRowBinding

class TelevisionAdapter(private val listener: OnItemClickListener) :
    PagingDataAdapter<Television, TelevisionAdapter.TelevisionViewHolder>(TelevisionDiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TelevisionViewHolder =
        TelevisionViewHolder(
            ItemRowBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: TelevisionViewHolder, position: Int) {
        val currentItem = getItem(position)
        if (currentItem != null) {
            holder.bind(currentItem)
        }
    }

    inner class TelevisionViewHolder(private val binding: ItemRowBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                val position = bindingAdapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val item = getItem(position)
                    if (item != null) {
                        listener.onItemClick(item)
                    }
                }
            }
        }

        fun bind(television: Television) =
            with(binding) {
                Glide.with(itemView)
                    .load("${television.baseUrl}${television.posterPath}")
                    .centerCrop()
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .error(R.drawable.ic_image_error)
                    .into(imageViewPoster)
                textViewTitle.text = television.name
                textViewOverview.text = television.overview
            }

    }

    interface OnItemClickListener {
        fun onItemClick(television: Television)
    }

    object TelevisionDiffCallback : DiffUtil.ItemCallback<Television>() {
        override fun areItemsTheSame(oldItem: Television, newItem: Television) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Television, newItem: Television) =
            oldItem == newItem
    }

}