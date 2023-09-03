package yz.l.core_tool.utils.system

import android.os.Build

/**
 * desc:
 * created by liyuzheng on 2023/9/3 11:31
 */
object RuntimeUtil {
    private const val PLATFORM_XIAOMI = "xiaomi"
    private const val PLATFORM_HUAWEI = "huawei"
    private const val PLATFORM_VIVO = "vivo"
    private const val PLATFORM_OPPO = "oppo"
    private const val PLATFORM_SAMSUNG = "samsung"
    const val PLATFORM_DEFAULT = "default"

    private val manufacturer by lazy { Build.MANUFACTURER.lowercase() }

    val platName by lazy {
        when {
            isMIUI() -> PLATFORM_XIAOMI
            isSamsung() -> PLATFORM_SAMSUNG
            isVivo() -> PLATFORM_VIVO
            isOppo() -> PLATFORM_OPPO
            isHuawei() -> PLATFORM_HUAWEI
            else -> PLATFORM_DEFAULT
        }
    }

    fun isHuawei(): Boolean {
        return manufacturer.contains("huawei")
    }

    fun isMIUI(): Boolean {
        return manufacturer.contains("xiaomi")
    }

    fun isSamsung(): Boolean {
        return manufacturer.contains("samsung")
    }

    fun isVivo(): Boolean {
        return manufacturer.contains("vivo")
    }

    fun isOppo(): Boolean {
        return manufacturer.contains("oppo")
    }
}