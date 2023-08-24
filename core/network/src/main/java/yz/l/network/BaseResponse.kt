package yz.l.network

/**
 * desc:
 * createed by liyuzheng on 2023/8/24 19:58
 */
abstract class BaseResponse

data class StringBaseResponse(val str: String) : BaseResponse() {
    override fun toString(): String {
        return str
    }
}

data class ErrorResponse(
    var code: Int,
    var mes: String
) : BaseResponse()
