package yz.l.feature_lottery.provider

import android.content.Context
import android.content.Intent
import com.google.auto.service.AutoService
import yz.l.feature_lottery.LotteriesAct
import yz.l.service.lottery.LotteryServiceAuto

/**
 * desc:
 * createed by liyuzheng on 2023/9/1 13:38
 */
@AutoService(LotteryServiceAuto::class)
class LotteryServiceAutoProvider : LotteryServiceAuto {
    override fun launchLotteriesAct(context: Context) {
        context.startActivity(Intent(context, LotteriesAct::class.java))
    }

    override fun init(app: Context) {

    }
}