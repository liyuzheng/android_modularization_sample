package yz.l.core_tool.ext

import yz.l.core_tool.BuildConfig
import yz.l.core_tool.utils.system.RuntimeUtil
import java.util.ServiceLoader


/**
 * desc:
 * createed by liyuzheng on 2023/8/28 17:42
 */
inline fun debug(block: () -> Unit) {
    if (BuildConfig.DEBUG) {
        block()
    }
}

inline fun release(block: () -> Unit) {
    if (!BuildConfig.DEBUG) {
        block()
    }
}

inline fun <reified T> getPlatformProxy(): T {
    val implList = ServiceLoader.load(T::class.java).toList()
    return runCatching {
        implList.find {
            it?.getSimpleNameLowerCase()?.contains(RuntimeUtil.platName) == true
        } as T
    }.getOrElse {
        implList.find {
            it?.getSimpleNameLowerCase()?.contains(RuntimeUtil.PLATFORM_DEFAULT) == true
        } as T
    }
}