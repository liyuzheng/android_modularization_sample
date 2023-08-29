package yz.l.fm

import yz.l.core_tool.common.BaseAct
import yz.l.service.lottery.LotteryProxy

/**
 * desc:
 * createed by liyuzheng on 2023/8/24 17:59
 */
class MainActivity : BaseAct() {
    override fun getLayoutId() = R.layout.main_act

    override fun setup() {
        LotteryProxy.launchLotteriesAct(this)
    }

    override fun onBackPressedCallback() {
        super.onBackPressedCallback()
        moveTaskToBack(false)
    }

    override var onBackPressedCallbackEnable = true

}