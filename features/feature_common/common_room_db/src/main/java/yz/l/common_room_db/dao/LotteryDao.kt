package yz.l.common_room_db.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import yz.l.common_room_db.eneities.LotteryEntity

/**
 * desc:
 * createed by liyuzheng on 2023/8/29 14:23
 */
@Dao
interface LotteryDao {
    @Query("SELECT * FROM LotteryEntity WHERE remoteName = :remoteName ORDER BY number desc")
    fun getLotteryPagingSource(
        remoteName: String
    ): PagingSource<Int, LotteryEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllAsync(mList: List<LotteryEntity>)

    @Query("DELETE FROM LotteryEntity WHERE remoteName = :remoteName")
    suspend fun clearLocalDataByRemoteNameAsync(remoteName: String)
}