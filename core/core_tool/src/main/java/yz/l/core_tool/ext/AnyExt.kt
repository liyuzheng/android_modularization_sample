package yz.l.core_tool.ext

/**
 * desc:
 * created by liyuzheng on 2023/9/3 13:02
 */
fun Any.getSimpleNameLowerCase(): String? = this::class.java.simpleName.lowercase()