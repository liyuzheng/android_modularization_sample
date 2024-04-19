package yz.l.data.diary

import yz.l.common_room_db.RoomDB
import yz.l.common_room_db.dao.DiaryDao
import yz.l.common_room_db.eneities.DiaryEntity

/**
 * desc:
 * created by liyuzheng on 2023/9/6 16:18
 */
object DiaryDB {
    private val dao: DiaryDao by lazy {
        RoomDB.INSTANCE.diaryDao()
    }

    suspend fun getDiaryListAsync(
        dateTimestamp: Long
    ): List<DiaryEntity> {
        val startDateTimestamp = dateTimestamp - 60 * 60 * 24 * 10
        val endDateTimestamp = dateTimestamp + 60 * 60 * 24 * 10
        return dao.getDiaryListAsync(startDateTimestamp, endDateTimestamp)
    }

    suspend fun insertAllAsync(items: MutableList<DiaryModel>) {
        dao.insertAllAsync(items.map {
            it.toDiaryEntity()
        })
    }

    suspend fun insertAsync(diaryModel: DiaryModel) {
        dao.insertAsync(diaryModel.toDiaryEntity())
    }

    suspend fun getDiaryAsync(dateTimestamp: Long): DiaryModel? {
        return dao.getDiaryAsync(dateTimestamp)?.toDiaryModel()
    }
}