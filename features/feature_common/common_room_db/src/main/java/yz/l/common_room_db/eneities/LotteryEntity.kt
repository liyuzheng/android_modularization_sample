package yz.l.common_room_db.eneities

import androidx.room.Entity

/**
 * desc:
 * createed by liyuzheng on 2023/8/29 14:21
 */
@Entity(primaryKeys = ["number", "lotteryType", "remoteName"])
data class LotteryEntity(
    val lotteryType: String,
    val numbers: MutableList<String>,
    val dateTime: String,
    val number: String,
    val remoteName: String
)