package yz.l.common_room_db.dbs

import yz.l.common_room_db.RoomDB
import yz.l.common_room_db.dao.CalendarDao
import yz.l.common_room_db.exts.toCalendarEntity
import yz.l.common_room_db.exts.toCalendarModel
import yz.l.data.diary.CalendarModel

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