package yz.l.common_room_db

import androidx.room.TypeConverter
import yz.l.core_tool.ext.toJson
import yz.l.core_tool.ext.toObject
import java.util.Date


/**
 * <pre>
 *     author: dhl
 *     date  : 2020/7/21
 *     desc  :
 * </pre>
 */
open class LocalTypeConverter {
    @TypeConverter
    fun json2StrListEntity(src: String?): List<String>? =
        src.toObject()

    @TypeConverter
    fun strList2Json(data: List<String>?): String? = data?.toJson()

    @TypeConverter
    fun date2Long(date: Date?): Long {
        return date?.time ?: System.currentTimeMillis()
    }

    @TypeConverter
    fun long2Date(time: Long): Date {
        return Date(time)
    }
}