package yz.l.network

import androidx.annotation.Keep

/**
 * desc:
 * createed by liyuzheng on 2023/3/9 14:52
 */
@Keep
open class ResponseException(val code: Int, override val message: String?) : Exception()