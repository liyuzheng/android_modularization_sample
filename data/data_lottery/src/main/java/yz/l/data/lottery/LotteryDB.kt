package yz.l.data.lottery

import yz.l.common_room_db.RoomDB
import yz.l.common_room_db.dao.LotteryDao
import yz.l.common_room_db.eneities.LotteryEntity

/**
 * desc:
 * createed by liyuzheng on 2023/8/29 14:20
 */
object LotteryDB {
    private val dao: LotteryDao by lazy {
        RoomDB.INSTANCE.lotteryDao()
    }

    fun getLotteryPagingSource(remoteName: String) = dao.getLotteryPagingSource(remoteName)

    suspend fun insertAll(mList: List<LotteryEntity>) {
        dao.insertAllAsync(mList)
    }

    suspend fun clearLocalDataByRemoteNameAsync(remoteName: String) {
        dao.clearLocalDataByRemoteNameAsync(remoteName)
    }
}