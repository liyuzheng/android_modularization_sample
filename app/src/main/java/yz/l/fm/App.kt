package yz.l.fm

import android.app.Application
import android.content.Context
import yz.l.feature_login.LoginApp


/**
 * desc:
 * createed by liyuzheng on 2023/8/24 17:07
 */
class App : Application() {
    companion object {
        lateinit var context: Context
            private set
    }

    override fun onCreate() {
        super.onCreate()
        context = this
        LoginApp.onCreate(this)
    }
}