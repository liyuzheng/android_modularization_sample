package yz.l.fm

import yz.l.core_tool.common.BaseAct
import yz.l.service_login.LoginProxy

/**
 * desc:
 * createed by liyuzheng on 2023/8/24 17:59
 */
class MainActivity : BaseAct() {
    companion object {
        var firstEnter = true
    }

    override fun getLayoutId() = R.layout.main_act

    override fun setup() {
        LoginProxy.launchLoginActivity(this)
    }

    override fun onBackPressedCallback() {
        super.onBackPressedCallback()
        moveTaskToBack(false)
    }

    override var onBackPressedCallbackEnable = true

}