package com.example.newsapp.data.api

import com.example.newsapp.app.utils.Cons.Companion.API_KEY
import com.example.newsapp.app.utils.Cons.Companion.BASE_URL
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitInstance {
    private fun requestInterceptor() = Interceptor { cain ->
        val url = cain.request()
            .url
            .newBuilder()
            .addQueryParameter("apiKey", API_KEY)
            .build()
        val request = cain.request()
            .newBuilder()
            .url(url)
            .build()
        return@Interceptor cain.proceed(request)

    }

    private fun okHttpClient(): OkHttpClient {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        return OkHttpClient.Builder()
            .addInterceptor(requestInterceptor())
            .addInterceptor(httpLoggingInterceptor)
            .connectTimeout(60, TimeUnit.SECONDS)
            .build()
    }

    private val retrofit by lazy(LazyThreadSafetyMode.NONE) {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val newsApi: NewsApi by lazy(LazyThreadSafetyMode.NONE) {
        retrofit.create(NewsApi::class.java)
    }
}