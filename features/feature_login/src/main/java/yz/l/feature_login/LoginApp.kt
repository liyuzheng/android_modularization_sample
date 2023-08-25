package yz.l.feature_login

import android.content.Context
import yz.l.feature_login.provider.LoginProvider

/**
 * desc:
 * createed by liyuzheng on 2023/8/24 18:24
 */
object LoginApp {
    lateinit var context: Context
        private set

    fun onCreate(context: Context) {
        LoginApp.context = context
        LoginProvider().init(context)
    }
}