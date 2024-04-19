package yz.l.data.diary

import com.google.gson.annotations.SerializedName
import yz.l.network.BaseResponse

/**
 * desc:
 * created by liyuzheng on 2023/9/6 14:35
 */
data class CalendarModelRes(
    val code: Int = 0,
    val msg: String = "",
    val data: CalendarModel = CalendarModel()
) : BaseResponse()

data class CalendarModel(
    var dateTimestamp: Long = 0,
    @SerializedName("date")
    var dateDesc: String = "",
    @SerializedName("weekDay")
    var dayOfWeek: Int = 0,
    @SerializedName("yearTips")
    var yearDesc: String = "",
    @SerializedName("type")
    var dayType: Int = 0,
    @SerializedName("typeDes")
    var dayTypeDesc: String = "",
    @SerializedName("chineseZodiac")
    var cZodiac: String = "",
    @SerializedName("solarTerms")
    var solarTerms: String = "",
    @SerializedName("lunarCalendar")
    var cCalendarDesc: String = "",
    @SerializedName("suit")
    var suit: String = "",
    @SerializedName("dayOfYear")
    var dayOfYear: Int = 0,
    @SerializedName("weekOfYear")
    var weekOfYear: Int = 0,
    @SerializedName("constellation")
    var constellation: String = ""
)

