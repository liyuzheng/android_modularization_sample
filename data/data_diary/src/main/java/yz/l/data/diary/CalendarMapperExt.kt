package yz.l.data.diary

import yz.l.common_room_db.eneities.CalendarEntity

/**
 * desc:
 * created by liyuzheng on 2023/9/6 16:11
 */
fun CalendarModel.toCalendarEntity() = CalendarEntity(
    dateTimestamp,
    dateDesc,
    dayOfWeek,
    yearDesc,
    dayType,
    dayTypeDesc,
    cZodiac,
    solarTerms,
    cCalendarDesc,
    suit,
    dayOfYear,
    weekOfYear,
    constellation
)

fun CalendarEntity.toCalendarModel() = CalendarModel(
    dateTimestamp,
    dateDesc,
    dayOfWeek,
    yearDesc,
    dayType,
    dayTypeDesc,
    cZodiac,
    solarTerms,
    cCalendarDesc,
    suit,
    dayOfYear,
    weekOfYear,
    constellation
)