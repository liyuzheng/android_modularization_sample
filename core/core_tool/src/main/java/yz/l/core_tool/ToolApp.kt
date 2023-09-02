package yz.l.core_tool

import android.annotation.SuppressLint
import android.content.Context

/**
 * desc:
 * createed by liyuzheng on 2023/9/2 12:41
 */
@SuppressLint("StaticFieldLeak")
object ToolApp {
    lateinit var context: Context
        private set

    fun onCreate(context: Context) {
        ToolApp.context = context
    }
}