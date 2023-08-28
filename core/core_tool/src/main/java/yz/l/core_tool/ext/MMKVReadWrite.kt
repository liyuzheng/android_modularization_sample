package yz.l.core_tool.ext

import android.os.Parcelable
import com.tencent.mmkv.MMKV
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

/**
 * desc:
 * createed by liyuzheng on 2023/8/26 13:59
 */
@Suppress("UNCHECKED_CAST")
abstract class MMKVReadWrite<T>(private val key: String, private val defaultValue: T) :
    ReadWriteProperty<Any, T> {
    abstract fun getMMKV(): MMKV

    override fun getValue(thisRef: Any, property: KProperty<*>): T {
        return with(getMMKV()) {
            when (defaultValue) {
                is Long -> getLong(key, defaultValue)
                is Float -> getFloat(key, defaultValue)
                is String -> getString(key, defaultValue)
                is Boolean -> getBoolean(key, defaultValue)
                is Int -> getInt(key, defaultValue)
                is Parcelable -> {
                    decodeParcelable(key, defaultValue.javaClass)
                        ?: defaultValue
                }

                else -> {
                    gson.fromJson(
                        getString(key, gson.toJson(defaultValue)),
                        requireNotNull(defaultValue)::class.java
                    )
                }
            }
        } as T
    }

    override fun setValue(thisRef: Any, property: KProperty<*>, value: T) {
        getMMKV().apply {
            when (value) {
                is Long -> putLong(key, value)
                is String -> putString(key, value)
                is Int -> putInt(key, value)
                is Boolean -> putBoolean(key, value)
                is Float -> putFloat(key, value)
                is Parcelable -> encode(key, value)
                else -> {
                    putString(key, gson.toJson(value))
                }
            }
        }
    }
}