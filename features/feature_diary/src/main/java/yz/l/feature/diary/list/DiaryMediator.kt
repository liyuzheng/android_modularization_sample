package yz.l.feature.diary.list

import androidx.paging.LoadType
import androidx.paging.PagingConfig
import androidx.room.withTransaction
import yz.l.common_paging.common.BaseRemoteMediator
import yz.l.common_room_db.RoomDB
import yz.l.common_room_db.eneities.LotteryEntity
import yz.l.core_tool.utils.CalendarUtil
import yz.l.data.common.RemoteDB
import yz.l.data.diary.DiaryDB
import yz.l.data.diary.DiaryModel
import yz.l.data.diary.PagingDiaryDB
import yz.l.data.diary.PagingDiaryModel

/**
 * desc:
 * created by liyuzheng on 2023/9/6 15:19
 */
class DiaryMediator :
    BaseRemoteMediator<LotteryEntity, DiaryModel>("DiaryMediator") {
    override suspend fun load(
        loadKey: String,
        loadType: LoadType,
        pageConfig: PagingConfig
    ): Boolean {
        RoomDB.INSTANCE.withTransaction {
            val list = mutableListOf<PagingDiaryModel>()
            val start =
                (loadKey.toLongOrNull()
                    ?: CalendarUtil.getCurrentDayTimestamp()) - 60 * 60 * 24 * 10
            val end = (loadKey.toLongOrNull()
                ?: CalendarUtil.getCurrentDayTimestamp()) + 60 * 60 * 24 * 10

            for (i in start until end step 60 * 60 * 24) {
                val diary = DiaryDB.getDiaryAsync(i)
                val diaryModel = if (diary == null) {
                    val temp = DiaryModel(i)
                    DiaryDB.insertAsync(temp)
                    temp
                } else {
                    diary
                }
                val pagingDiaryModel = PagingDiaryModel(diaryModel.dateTimestamp)
                list.add(pagingDiaryModel)
            }
            PagingDiaryDB.insertAllAsync(list)
        }
        return false
    }

    override fun defaultRefreshLoadKey(remoteName: String): String {
        return CalendarUtil.getCurrentDayTimestamp().toString()
    }

    override suspend fun initPrependKey(remoteName: String): String? {
        return RemoteDB.getRemoteKeysAsync(remoteName)?.prepend
    }

    override suspend fun clearLocalData() {
        PagingDiaryDB.clearPagingDiaryEntity()
    }
}