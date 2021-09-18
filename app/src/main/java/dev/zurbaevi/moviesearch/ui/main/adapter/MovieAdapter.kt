package dev.zurbaevi.moviesearch.ui.main.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import dev.zurbaevi.moviesearch.data.model.MovieModel
import dev.zurbaevi.moviesearch.databinding.ItemMovieLayoutBinding

class MovieAdapter(private val clickListener: OnItemClickListener) :
    ListAdapter<MovieModel, MovieAdapter.MovieViewHolder>(DiffUtilCallback) {

    object DiffUtilCallback : DiffUtil.ItemCallback<MovieModel>() {
        override fun areItemsTheSame(oldItem: MovieModel, newItem: MovieModel): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: MovieModel, newItem: MovieModel): Boolean {
            return oldItem == newItem
        }
    }

    interface OnItemClickListener {
        fun onItemClick(taskEntry: MovieModel)
    }

    inner class MovieViewHolder(private val binding: ItemMovieLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.apply {
                root.setOnClickListener {
                    val position = adapterPosition
                    if (position != RecyclerView.NO_POSITION) {
                        clickListener.onItemClick(getItem(position))
                    }
                }
            }
        }

        fun bind(movieModel: MovieModel) {
            binding.apply {
                textViewTitle.text = movieModel.title
                textViewType.text = movieModel.type
                textViewYear.text = movieModel.year
                Glide.with(imageViewPoster.context)
                    .load(movieModel.poster)
                    .into(imageViewPoster)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        MovieViewHolder(
            ItemMovieLayoutBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) =
        holder.bind(getItem(position))

}