package yz.l.core_tool.ext

import yz.l.core_tool.BuildConfig


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