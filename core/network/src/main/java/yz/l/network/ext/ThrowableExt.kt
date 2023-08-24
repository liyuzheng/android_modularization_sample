package yz.l.network.ext

import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import yz.l.core_tool.ext.toObject
import yz.l.network.ErrorResponse
import yz.l.network.NetworkExceptionConstantCode

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