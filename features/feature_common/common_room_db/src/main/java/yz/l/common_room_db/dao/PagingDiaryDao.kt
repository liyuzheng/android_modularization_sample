package yz.l.common_room_db.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import yz.l.common_room_db.eneities.PagingDiaryEntity

/**
 * desc:
 * created by liyuzheng on 2023/9/6 17:20
 */
@Dao
interface PagingDiaryDao {
    @Query("SELECT * FROM PagingDiaryEntity ORDER BY dateTimestamp")
    fun getPagingDiaryPagingSource(
    ): PagingSource<Int, PagingDiaryEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllAsync(mList: List<PagingDiaryEntity>)

    @Query("DELETE FROM PagingDiaryEntity")
    suspend fun clearPagingDiaryEntity()
}