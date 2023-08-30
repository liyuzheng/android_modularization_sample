package yz.l.data_user

import androidx.annotation.Keep

/**
 * desc:
 * createed by liyuzheng on 2023/8/30 16:01
 */
sealed class LoginResult {
    @Keep
    class Success(val nothing: Nothing? = null) : LoginResult()

    @Keep
    class UnKnown(val nothing: Nothing? = null) : LoginResult()

    @Keep
    class Cancel(val nothing: Nothing? = null) : LoginResult()

    @Keep
    class Guest(val nothing: Nothing? = null) : LoginResult()

}

inline fun LoginResult.onSuccess(block: () -> Unit) {
    if (this is LoginResult.Success) {
        block()
    }
}

inline fun LoginResult.onUnKnown(block: () -> Unit) {
    if (this is LoginResult.UnKnown) {
        block()
    }
}

inline fun LoginResult.onCancel(block: () -> Unit) {
    if (this is LoginResult.Cancel) {
        block()
    }
}

inline fun LoginResult.onGuest(block: () -> Unit) {
    if (this is LoginResult.Guest) {
        block()
    }
}