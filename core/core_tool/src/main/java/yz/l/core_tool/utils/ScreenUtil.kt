package yz.l.core_tool.utils

import android.content.Context
import yz.l.core_tool.ToolApp

object ScreenUtil {
    private var density = 0f

    fun dip2px(dipValue: Float): Int {
        if (density == 0f) {
            init(ToolApp.context)
        }
        return (dipValue * density + 0.5f).toInt()
    }

    fun px2dip(pxValue: Float): Int {
        if (density == 0f) {
            init(ToolApp.context)
        }
        return (pxValue / density + 0.5f).toInt()
    }


    fun init(context: Context) {
        val dm = context.applicationContext.resources.displayMetrics
        density = dm.density
    }
}