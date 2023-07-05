package com.tiara.appmovie.ui.movie


import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tiara.appmovie.data.client.ApiClient
import com.tiara.appmovie.data.model.ListResponse
import com.tiara.appmovie.data.model.MovieResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MovieViewModel: ViewModel() {
    private val movies = MutableLiveData<List<MovieResponse>>()

    fun setMovies(){
        ApiClient.instance.getMovies()
            .enqueue(object : Callback<ListResponse<MovieResponse>> {
                override fun onResponse(
                    call: Call<ListResponse<MovieResponse>>,
                    response: Response<ListResponse<MovieResponse>>
                ) {
                    if (response.isSuccessful){
                        movies.postValue(response.body()?.results)
                    }
                }

                override fun onFailure(call: Call<ListResponse<MovieResponse>>, t: Throwable) {
                    Log.d(this@MovieViewModel::class.java.simpleName
                        , t.message.toString())
                }

            })
    }

    fun getMovies() = movies

}