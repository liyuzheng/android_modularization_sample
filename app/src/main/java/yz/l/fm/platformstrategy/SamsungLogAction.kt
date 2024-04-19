package yz.l.fm.platformstrategy

import android.os.Handler
import android.os.Looper
import android.os.Message
import android.util.Log
import com.google.auto.service.AutoService

/**
 * desc:
 * created by liyuzheng on 2023/9/3 12:25
 */
@AutoService(ILogPlatformAction::class)
class SamsungLogAction : DefaultLogAction() {
    override fun log() {
        Log.v("ssssss", "current is Samsung")
        val a = Handler(Looper.getMainLooper())
        a.sendMessage(Message.obtain())
    }
}