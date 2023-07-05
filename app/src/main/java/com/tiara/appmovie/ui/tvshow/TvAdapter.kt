package com.tiara.appmovie.ui.tvshow

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.tiara.appmovie.data.model.TvShowResponse
import com.tiara.appmovie.databinding.ItemMovieOrTvshowBinding
import com.tiara.appmovie.utils.BASE_URL_API_IMAGE
import com.tiara.appmovie.utils.DivTvShowCallback
import com.tiara.appmovie.utils.POSTER_SIZE_W185
import com.tiara.appmovie.utils.loadImageTvShow


class TvAdapter : RecyclerView.Adapter<TvAdapter.ViewHolder>() {

    private var listener: ((TvShowResponse) -> Unit)? = null

    var tvshow = mutableListOf<TvShowResponse>()
        set(value) {
            val callback = DivTvShowCallback(field, value)
            val result = DiffUtil.calculateDiff(callback)
            field.clear()
            field.addAll(value)
            result.dispatchUpdatesTo(this)
        }

    inner class ViewHolder(private val binding: ItemMovieOrTvshowBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(tvshow: TvShowResponse) {
            binding.apply {
                tvshow.poster?.let {
                    poster.loadImageTvShow("$BASE_URL_API_IMAGE$POSTER_SIZE_W185${tvshow.poster}")
                }
                tvTitle.text = tvshow.name
                tvDesc.text = if (tvshow.desc?.isNotBlank() as Boolean) tvshow.desc else "No Description"

                listener?.let { itemView.setOnClickListener { it(tvshow) } }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemMovieOrTvshowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(tvshow[position])
    }

    override fun getItemCount(): Int = tvshow.size

    fun onClick(listener: ((TvShowResponse) -> Unit)?){
        this.listener = listener
    }


}