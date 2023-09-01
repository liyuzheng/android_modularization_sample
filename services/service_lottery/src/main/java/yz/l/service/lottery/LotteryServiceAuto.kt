package yz.l.service.lottery

import android.content.Context
import yz.l.core_router.RouterService

/**
 * desc:
 * createed by liyuzheng on 2023/9/1 13:38
 */
interface LotteryServiceAuto : RouterService {
    fun launchLotteriesAct(context: Context)
}