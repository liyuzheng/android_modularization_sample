package yz.l.common_room_db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import yz.l.common_room_db.eneities.CalendarEntity

/**
 * desc:
 * created by liyuzheng on 2023/9/6 16:04
 */
@Dao
interface CalendarDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAsync(calendarEntity: CalendarEntity)

    @Query("SELECT * FROM CalendarEntity WHERE dateTimestamp = :dateTimestamp ")
    suspend fun getCalendarAsync(dateTimestamp: Long): CalendarEntity?

}