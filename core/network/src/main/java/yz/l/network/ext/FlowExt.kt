package yz.l.network.ext

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach
import yz.l.network.NetworkExceptionConstantCode
import yz.l.network.Result

/**
 * desc:
 * createed by liyuzheng on 2023/6/2 10:25
 */
inline fun <reified T : Any> Flow<T>.completedIn(
    scope: CoroutineScope,
    crossinline callback: (r: Result<T>) -> Unit
) {
    callback(Result.Loading(true))
    this@completedIn.catch {
        val pair = it.toPair()
        if (pair.first == NetworkExceptionConstantCode.CANCEL) {
            callback(Result.Cancel())
        } else {
            callback(Result.Failure(pair))
        }
    }.onEach {
        callback(Result.Success(it))
    }.catch {
        val pair = it.toPair()
        if (pair.first == NetworkExceptionConstantCode.CANCEL) {
            callback(Result.Cancel())
        } else {
            callback(Result.Failure(pair))
        }
    }.onCompletion {
        callback(Result.Completion())
        callback(Result.Loading(false))
    }.launchIn(scope).start()
}