package com.tiara.appmovie.ui.movie

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.tiara.appmovie.data.model.MovieResponse
import com.tiara.appmovie.databinding.ItemMovieOrTvshowBinding
import com.tiara.appmovie.utils.BASE_URL_API_IMAGE
import com.tiara.appmovie.utils.MovieCallback
import com.tiara.appmovie.utils.POSTER_SIZE_W185
import com.tiara.appmovie.utils.loadImageMovie

class MovieAdapter : RecyclerView.Adapter<MovieAdapter.ViewHolder>() {

    private var listener: ((MovieResponse) -> Unit)? = null

    var movies = mutableListOf<MovieResponse>()
        set(value) {
            val callback = MovieCallback(field, value)
            val result = DiffUtil.calculateDiff(callback)
            field.clear()
            field.addAll(value)
            result.dispatchUpdatesTo(this)
        }

    inner class ViewHolder(private val binding: ItemMovieOrTvshowBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(movie: MovieResponse) {
            binding.apply {
                movie.poster?.let {
                    poster.loadImageMovie("$BASE_URL_API_IMAGE$POSTER_SIZE_W185$it")
                }
                tvTitle.text = movie.name
                tvDesc.text = if (movie.desc?.isNotBlank() as Boolean) movie.desc else "No Description"

                listener?.let { itemView.setOnClickListener { it(movie) } }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemMovieOrTvshowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(movies[position])
    }

    override fun getItemCount(): Int = movies.size

    fun onClick(listener: ((MovieResponse) -> Unit)?){
        this.listener = listener
    }
}