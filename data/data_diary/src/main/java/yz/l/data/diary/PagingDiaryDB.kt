package yz.l.data.diary

import yz.l.common_room_db.RoomDB
import yz.l.common_room_db.dao.PagingDiaryDao

/**
 * desc:
 * created by liyuzheng on 2023/9/6 17:23
 */
object PagingDiaryDB {
    private val dao: PagingDiaryDao by lazy {
        RoomDB.INSTANCE.pagingDiaryDao()
    }

    fun getPagingDiaryPagingSource(
    ) = dao.getPagingDiaryPagingSource()

    suspend fun insertAllAsync(mList: List<PagingDiaryModel>) = dao.insertAllAsync(mList.map {
        it.toPagingDiaryEntity()
    })

    suspend fun clearPagingDiaryEntity() = dao.clearPagingDiaryEntity()
}