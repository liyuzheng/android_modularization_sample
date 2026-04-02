package yz.l.service.lottery

import android.content.Context
import yz.l.core_router.Router
import yz.l.core_tool.ext.runWhenNotNull
import java.util.ServiceLoader

/**
 * desc:
 * createed by liyuzheng on 2023/8/29 14:08
 */
object LotteryProxy {
    private val provider by lazy {
        Router.getProvider(LotteryServicePath.LOTTERY_SERVICE_PATH_CODE) as LotteryService
    }

    private val providerAuto: LotteryServiceAuto? by lazy {
        ServiceLoader.load(LotteryServiceAuto::class.java).toMutableList().getOrNull(0)
    }

    fun launchLotteriesAct(context: Context) {
        provider.launchLotteriesAct(context)
    }

    fun launchLotteriesActAuto(context: Context) {
        providerAuto.runWhenNotNull("LotteryServiceAuto does not impl") {
            it.launchLotteriesAct(context)
        }

    }
}