package yz.l.core_tool.ext

import yz.l.core_tool.utils.ScreenUtil

/**
 * desc:
 * createed by liyuzheng on 2023/9/2 12:44
 */
val Float.dp
    get() = ScreenUtil.dip2px(this)

val Float.px
    get() = ScreenUtil.px2dip(this)

val Int.dp
    get() = toFloat().dp

val Int.px
    get() = toFloat().px
