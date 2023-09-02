package yz.l.fm

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import com.tencent.mmkv.MMKV
import yz.l.common_room_db.RoomDB
import yz.l.core_tool.ToolApp
import yz.l.feature_login.LoginApp
import yz.l.feature_lottery.LotteryApp


/**
 * desc:
 * createed by liyuzheng on 2023/8/24 17:07
 */
class App : Application() {
    companion object {
        @SuppressLint("StaticFieldLeak")
        lateinit var context: Context
            private set
    }

    override fun onCreate() {
        super.onCreate()
        MMKV.initialize(this)
        RoomDB.init(this)
        context = this
        ToolApp.onCreate(this)
        LoginApp.onCreate(this)
        LotteryApp.onCreate(this)
    }
}