package yz.l.network

/**
 * desc:
 * createed by liyuzheng on 2023/3/3 12:21
 */
class ResponseEmpty(
    code: Int = NetworkExceptionConstantCode.EMPTY_ERROR,
    mes: String = "error",
    val icon: Int = 0,
    val extra: MutableMap<String, Any>? = null
) : ResponseException(code, mes)
