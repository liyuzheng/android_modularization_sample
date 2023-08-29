package yz.l.service.lottery

import android.content.Context
import yz.l.core_router.Router

/**
 * desc:
 * createed by liyuzheng on 2023/8/29 14:08
 */
object LotteryProxy {
    private val provider by lazy {
        Router.getProvider(LotteryServicePath.LOTTERY_SERVICE_PATH_CODE) as LotteryService
    }

    fun launchLotteriesAct(context: Context) {
        provider.launchLotteriesAct(context)
    }
}