package yz.l.feature_lottery

import android.annotation.SuppressLint
import android.content.Context
import yz.l.feature_lottery.provider.LotteryProvider

/**
 * desc:
 * createed by liyuzheng on 2023/8/24 18:24
 */
@SuppressLint("StaticFieldLeak")
object LotteryApp {
    lateinit var context: Context
        private set

    fun onCreate(context: Context) {
        LotteryApp.context = context
        LotteryProvider().init(context)
    }
}