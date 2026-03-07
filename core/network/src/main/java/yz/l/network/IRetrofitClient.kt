package yz.l.network

import retrofit2.Retrofit

/**
 * desc:
 * created by liyuzheng on 2025/4/3 2:39
 */
interface IRetrofitClient {
    fun provideRetrofit(): Retrofit
}