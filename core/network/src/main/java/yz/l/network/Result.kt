package yz.l.network

import androidx.annotation.Keep

@Keep
sealed class Result<out T> {
    @Keep
    class Success<out T>(val value: T) : Result<T>()

    @Keep
    class Failure(val pair: Pair<Int, String>) : Result<Nothing>()

    @Keep
    class Completion(val nothing: Nothing? = null) : Result<Nothing>()

    @Keep
    class Cancel(val nothing: Nothing? = null) : Result<Nothing>()
}

inline fun <reified T> Result<T>.onSuccess(block: (T) -> Unit) {
    if (this is Result.Success) {
        block(value)
    }
}

inline fun <reified T> Result<T>.onFailure(block: (p: Pair<Int, String>) -> Unit) {
    if (this is Result.Failure) {
        block(pair)
    }
}

inline fun <reified T> Result<T>.onCompletion(block: () -> Unit) {
    if (this is Result.Completion) {
        block()
    }
}

inline fun <reified T> Result<T>.onCancel(block: () -> Unit) {
    if (this is Result.Cancel) {
        block()
    }
}