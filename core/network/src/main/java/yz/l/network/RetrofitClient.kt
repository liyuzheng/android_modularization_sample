package yz.l.network

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit

/**
 * desc:
 * createed by liyuzheng on 2023/8/24 19:48
 */
object RetrofitClient {
    private const val BASE_URL = "http://www.baidu.com"

    private fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .connectTimeout(10, TimeUnit.SECONDS)
            .writeTimeout(10, TimeUnit.SECONDS)
            .readTimeout(10, TimeUnit.SECONDS)
            .retryOnConnectionFailure(true)
            .build()
    }

    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .client(provideOkHttpClient())
            .baseUrl(BASE_URL)
            .addConverterFactory(StringConverterFactory())
            .build()
    }
}