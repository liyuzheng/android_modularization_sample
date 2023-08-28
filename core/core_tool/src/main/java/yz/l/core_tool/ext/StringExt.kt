package yz.l.core_tool.ext

import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder

/**
 * desc:
 * createed by liyuzheng on 2023/8/24 20:40
 */
val gson: Gson = GsonBuilder().apply {
    setLenient()
    setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
    serializeSpecialFloatingPointValues()
}.disableHtmlEscaping().create()

inline fun <reified T> String?.toObject(): T? {
    this ?: return null
    return gson.transform {
        it.fromJson(this@toObject, T::class.java)
    }
}

fun Any.toJson(): String? = gson.toJson(this)