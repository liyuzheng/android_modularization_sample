package yz.l.feature_lottery

import androidx.paging.LoadType
import androidx.paging.PagingConfig
import androidx.room.withTransaction
import yz.l.common_paging.common.BaseRemoteMediator
import yz.l.common_room_db.RoomDB
import yz.l.common_room_db.eneities.LotteryEntity
import yz.l.data.common.RemoteDB
import yz.l.data.common.RemoteModel
import yz.l.data.lottery.LotteryDB
import yz.l.data.lottery.LotteryList
import yz.l.data.lottery.LotteryModel
import yz.l.data.lottery.toLotteryEntity
import yz.l.network.ext.repo
import yz.l.network.ext.request

/**
 * desc:
 * createed by liyuzheng on 2023/8/29 14:48
 */
class LotteryMediator(private val queryStr: String) :
    BaseRemoteMediator<LotteryEntity, LotteryModel>("LotteryMediator") {
    override suspend fun load(
        loadKey: String,
        loadType: LoadType,
        pageConfig: PagingConfig
    ): Boolean {
        val repo = repo {
            api { loadKey.ifBlank { queryStr } }
        }

        val result = repo.request<LotteryList>()

        val lotteryEntities = result.data.map {
            it.toLotteryEntity(remoteName)
        }
        RoomDB.INSTANCE.withTransaction {
            if (loadType == LoadType.REFRESH) {
                //拿到结果后，如果判断出是刷新，先清空数据库
                clearLocalData()
            }
            LotteryDB.insertAll(lotteryEntities)
            RemoteDB.insertAsync(RemoteModel(remoteName, result.next))
        }
        return result.next.isBlank()
    }

    override suspend fun clearLocalData() {
        LotteryDB.clearLocalDataByRemoteNameAsync(remoteName)
    }
}