package ru.trinitydigital.youtube.data.remote

import okhttp3.Interceptor
import okhttp3.Response

class ApiKeyInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {

        val original = chain.request()
        val originalHttpUrl = original.url

        val url = originalHttpUrl.newBuilder()
            .addQueryParameter("key", "AIzaSyC5xOKtR05SHjyWVvBXC6850AMbO4_JkLo")
            .build()

        val requestBuilder = original.newBuilder()
            .url(url)
            .build()

        return chain.proceed(requestBuilder)
    }
}