package yz.l.common_room_db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import yz.l.common_room_db.eneities.DiaryEntity

/**
 * desc:
 * created by liyuzheng on 2023/9/6 16:07
 */
@Dao
interface DiaryDao {
    @Query("SELECT * FROM DiaryEntity WHERE dateTimestamp >= :startDateTimestamp and dateTimestamp <= :endDateTimestamp ORDER BY dateTimestamp desc")
    suspend fun getDiaryListAsync(
        startDateTimestamp: Long,
        endDateTimestamp: Long
    ): List<DiaryEntity>

    @Query("SELECT * FROM DiaryEntity WHERE dateTimestamp = :dateTimestamp")
    suspend fun getDiaryAsync(dateTimestamp: Long): DiaryEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllAsync(items: List<DiaryEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAsync(diaryEntity: DiaryEntity)
}