package yz.l.common_room_db.dbs

import yz.l.common_room_db.RoomDB
import yz.l.common_room_db.dao.PagingDiaryDao
import yz.l.common_room_db.exts.toPagingDiaryEntity
import yz.l.data.diary.PagingDiaryModel

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