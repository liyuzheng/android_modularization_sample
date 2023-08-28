package yz.l.core_tool.ext

import android.util.SparseArray

/**
 * desc:
 * createed by liyuzheng on 2023/3/3 13:54
 */

fun <T> sparse() = SparseArray<T>()
fun <T> sparse(vararg params: Pair<Int, T>) =
    SparseArray<T>().apply {
        putAll(params)
    }
fun <T> SparseArray<in T>.putAll(pairs: Array<out Pair<Int, T>>) {
    for ((key, value) in pairs) {
        put(key, value)
    }
}


