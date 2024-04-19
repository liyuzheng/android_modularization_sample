package yz.l.core_tool.utils

import java.util.Calendar

/**
 * desc:
 * created by liyuzheng on 2023/9/6 15:23
 */
object CalendarUtil {
    fun getCurrentDayTimestamp(timestamp: Long = System.currentTimeMillis()): Long {
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = timestamp
        calendar.set(Calendar.HOUR_OF_DAY, 0)
        calendar.set(Calendar.MINUTE, 0)
        calendar.set(Calendar.SECOND, 0)
        calendar.set(Calendar.MILLISECOND, 0)
        return calendar.timeInMillis
    }
}