package com.tiara.appmovie.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class TvShowResponse(
    @SerializedName("id")
    var id: Int,
    @SerializedName("name")
    var name: String,
    @SerializedName("overview")
    var desc: String?,
    @SerializedName("poster_path")
    var poster: String?,
    @SerializedName("backdrop_path")
    var imgPreview: String?,
    @SerializedName("release_date")
    var releaseDate: String?
): Parcelable
