package yz.l.core_tool.ext

import android.util.Log

/**
 * desc:
 * created by liyuzheng on 2023/9/3 13:02
 */
const val TAG = "AnyExt"
fun Any.getSimpleNameLowerCase(): String = this::class.java.simpleName.lowercase()

inline fun <reified T> T?.runWhenNotNull(desc: String, block: (T) -> Unit) {
    if (this == null)
        Log.v(TAG, "$desc 没有实现类！")
    else
        runWhenNotNull(block)
}

inline fun <reified T> T?.runWhenNotNull(block: (T) -> Unit) {
    this?.run(block)
}
