package yz.l.fm.platformstrategy

import android.util.Log
import com.google.auto.service.AutoService

/**
 * desc:
 * created by liyuzheng on 2023/9/3 12:28
 */
@AutoService(ILogPlatformAction::class)
open class DefaultLogAction : ILogPlatformAction {
    override fun log() {
        Log.v("ssssss", "current is Default")
    }
}