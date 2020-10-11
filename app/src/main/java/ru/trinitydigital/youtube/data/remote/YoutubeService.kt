package ru.trinitydigital.youtube.data.remote

import retrofit2.http.GET
import retrofit2.http.Query
import ru.trinitydigital.youtube.data.model.MainResponceYoutube

interface YoutubeService {

    @GET("videos")
    suspend fun getPopularVideos(
        @Query("chart") chart: String
    ): MainResponceYoutube
}