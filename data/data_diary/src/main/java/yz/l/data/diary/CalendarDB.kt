package yz.l.data.diary

import yz.l.common_room_db.RoomDB
import yz.l.common_room_db.dao.CalendarDao

/**
 * desc:
 * created by liyuzheng on 2023/9/6 16:16
 */
object CalendarDB {
    private val dao: CalendarDao by lazy {
        RoomDB.INSTANCE.calendarDao()
    }

    suspend fun getCalendarAsync(dateTimestamp: Long) =
        dao.getCalendarAsync(dateTimestamp)?.toCalendarModel()

    suspend fun insertAsync(calendarEntity: CalendarModel) {
        dao.insertAsync(calendarEntity.toCalendarEntity())
    }
}