package yz.l.data.diary

/**
 * desc:
 * created by liyuzheng on 2023/9/6 17:26
 */
data class PagingDiaryModel(
    var dateTimestamp: Long = 0,
    var calendarModel: CalendarModel? = null,
    var text: String? = null
)