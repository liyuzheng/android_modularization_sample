package yz.l.common_room_db.exts

import yz.l.common_room_db.eneities.LotteryEntity
import yz.l.data.lottery.LotteryModel

/**
 * desc:
 * createed by liyuzheng on 2023/8/29 14:20
 */
fun LotteryModel.toLotteryEntity(remoteName: String) = LotteryEntity(
    lotteryType = this.lotteryType,
    numbers = this.numbers,
    number = this.number,
    dateTime = this.dateTime,
    remoteName = remoteName
)

fun LotteryEntity.toLotteryModel() = LotteryModel(
    lotteryType = this.lotteryType,
    numbers = this.numbers,
    number = this.number,
    dateTime = this.dateTime
)