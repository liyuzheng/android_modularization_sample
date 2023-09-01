package yz.l.service.lottery

import android.content.Context
import yz.l.core_router.Router
import java.util.ServiceLoader

/**
 * desc:
 * createed by liyuzheng on 2023/8/29 14:08
 */
object LotteryProxy {
    private val provider by lazy {
        Router.getProvider(LotteryServicePath.LOTTERY_SERVICE_PATH_CODE) as LotteryService
    }

    private val providerAuto by lazy {
        ServiceLoader.load(LotteryServiceAuto::class.java).toList()[0]
    }

    fun launchLotteriesAct(context: Context) {
        provider.launchLotteriesAct(context)
    }

    fun launchLotteriesActAuto(context: Context) {
        providerAuto.launchLotteriesAct(context)
    }
}