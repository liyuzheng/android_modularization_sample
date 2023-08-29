package yz.l.feature_lottery.provider

import android.content.Context
import android.content.Intent
import yz.l.core_router.Router
import yz.l.feature_lottery.LotteriesAct
import yz.l.service.lottery.LotteryService
import yz.l.service.lottery.LotteryServicePath

/**
 * desc:
 * createed by liyuzheng on 2023/8/29 18:49
 */
class LotteryProvider : LotteryService {
    override fun launchLotteriesAct(context: Context) {
        context.startActivity(Intent(context, LotteriesAct::class.java))
    }

    override fun init(app: Context) {
        Router.register(LotteryServicePath.LOTTERY_SERVICE_PATH_CODE, this)
    }

}