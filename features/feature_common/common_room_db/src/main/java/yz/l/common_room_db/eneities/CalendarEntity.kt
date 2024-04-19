package yz.l.common_room_db.eneities

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * desc:
 * created by liyuzheng on 2023/9/6 15:57
 */
@Entity
data class CalendarEntity(
    @PrimaryKey
    var dateTimestamp: Long = 0,
    var dateDesc: String = "",
    var dayOfWeek: Int = 0,
    var yearDesc: String = "",
    var dayType: Int = 0,
    var dayTypeDesc: String = "",
    var cZodiac: String = "",
    var solarTerms: String = "",
    var cCalendarDesc: String = "",
    var suit: String = "",
    var dayOfYear: Int = 0,
    var weekOfYear: Int = 0,
    var constellation: String = ""
)