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
            onNewIntent(intent)
            finish()
            return
        }
        startActivity(Intent(this@SplashAct, MainActivity::class.java))
        finish()
    }
}