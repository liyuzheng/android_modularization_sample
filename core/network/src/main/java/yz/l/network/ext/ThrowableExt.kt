package yz.l.network.ext

import com.google.gson.JsonSyntaxException
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import yz.l.core_tool.ext.toObject
import yz.l.network.ErrorMessageBean
import yz.l.network.ErrorResponse
import yz.l.network.NetworkExceptionConstantCode
import yz.l.network.ResponseException

/**
 * desc:
 * createed by liyuzheng on 2023/6/2 10:25
 */
suspend fun Throwable.toPair(): Pair<Int, String> {
    return when (val e = this) {
        is HttpException -> {
            runCatching {
                val errorJson = withContext(Dispatchers.IO) {
                    e.response()?.errorBody()?.string()
                }
                val errorRsp: ErrorResponse? = withContext(Dispatchers.Default) {
                    errorJson.toObject()
                }
                Pair(
                    errorRsp?.code ?: NetworkExceptionConstantCode.UN_KNOWN,
                    errorRsp?.mes ?: ""
                )
            }.getOrElse {
                Pair(NetworkExceptionConstantCode.CANCEL, "error")
            }
        }

        is CancellationException -> {
            Pair(NetworkExceptionConstantCode.CANCEL, "")
        }

        else -> {
            Pair(NetworkExceptionConstantCode.CANCEL, "error")
        }
    }
}

fun Throwable.toResponseException(): ResponseException {
    val exception = when (val e = this) {
        is HttpException -> {
            try {
                val errorJson = e.response()?.errorBody()?.string()
                val errorRsp: ErrorMessageBean =
                    errorJson.toObject() ?: throw JsonSyntaxException("json转换失败")
                ResponseException(
                    errorRsp.error.code,
                    "HttpException:${errorRsp.error.message}"
                )
            } catch (ex: JsonSyntaxException) {
                ResponseException(
                    NetworkExceptionConstantCode.JSON_SYNTAX_EXCEPTION,
                    "HttpException: json error ${ex.message}"
                )
            } catch (ex: Exception) {
                ResponseException(
                    NetworkExceptionConstantCode.UN_KNOWN,
                    "HttpException: unknown ${ex.message}"
                )
            }
        }

        is CancellationException -> {
            ResponseException(NetworkExceptionConstantCode.CANCEL, "cancel")
        }

        is JsonSyntaxException -> {
            ResponseException(
                NetworkExceptionConstantCode.JSON_SYNTAX_EXCEPTION,
                "json error ${e.message}"
            )
        }

        else -> {
            ResponseException(
                NetworkExceptionConstantCode.NETWORK_ERROR,
                "network_error ${e.message}"
            )
        }
    }
    return exception
}