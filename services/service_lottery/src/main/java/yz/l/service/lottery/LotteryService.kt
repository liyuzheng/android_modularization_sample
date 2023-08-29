package yz.l.service.lottery

import android.content.Context
import yz.l.core_router.RouterService

/**
 * desc:
 * createed by liyuzheng on 2023/8/29 14:08
 */
interface LotteryService : RouterService {
    fun launchLotteriesAct(context: Context)
}