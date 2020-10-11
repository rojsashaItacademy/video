package ru.trinitydigital.youtube.data.model

data class MainResponceYoutube(
    val kind: String,
    val etag: String,
    val nextPageToken: String,
    val pageInfo: PageInfo,
    val items: List<Items>,
)

data class PageInfo(
    val totalResults: Int,
    val resultsPerPage: Int
)

data class Items(
    val kind: String,
    val etag: String,
    val id: String
)
