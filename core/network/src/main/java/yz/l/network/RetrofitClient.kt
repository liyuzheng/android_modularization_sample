package yz.l.network

import android.util.Log
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import yz.l.core_tool.ext.debug
import java.util.concurrent.TimeUnit

/**
 * desc:
 * createed by liyuzheng on 2023/8/24 19:48
 */
object RetrofitClient {
    private const val BASE_URL = "http://www.baidu.com"

    private fun provideOkHttpClient(): OkHttpClient {
        var builder = OkHttpClient.Builder()
            .connectTimeout(10, TimeUnit.SECONDS)
            .writeTimeout(10, TimeUnit.SECONDS)
            .readTimeout(10, TimeUnit.SECONDS)
            .retryOnConnectionFailure(true)
        debug {
            builder = builder.addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
        }
        return builder.build()
    }

    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .client(provideOkHttpClient())
            .baseUrl(BASE_URL)
            .addConverterFactory(StringConverterFactory())
            .build()
    }
}