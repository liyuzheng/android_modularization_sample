package yz.l.core_tool.utils

import android.content.Context
import android.content.res.ColorStateList
import android.os.Build
import androidx.annotation.ColorRes
import androidx.appcompat.content.res.AppCompatResources
import yz.l.core_tool.ToolApp

/**
 * desc:
 * created by liyuzheng on 2023/9/2 15:59
 */
@Suppress("DEPRECATION")
object ResourceUtil {
    fun getColor(context: Context? = null, @ColorRes resId: Int): Int {
        val ctx = context ?: ToolApp.context
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            ctx.resources.getColor(resId, null)
        } else {
            ctx.resources.getColor(resId)
        }
    }

    fun getColorStateList(context: Context? = null, @ColorRes resId: Int): ColorStateList {
        val ctx = context ?: ToolApp.context
        return AppCompatResources.getColorStateList(ctx, resId)
    }
}