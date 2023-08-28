package yz.l.fm

import android.content.Intent
import yz.l.core_tool.common.BaseAct

/**
 * desc:
 * createed by liyuzheng on 2023/8/28 21:05
 */
class SplashAct : BaseAct() {
    override fun getLayoutId() = 0

    override fun setup() {
        if ((intent.flags and Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT != 0) || !isTaskRoot) {
            //结束 activity
            //判断是否是外部进来，是否需要跳转
            onNewIntent(intent)
            finish()
            return
        }
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }
}