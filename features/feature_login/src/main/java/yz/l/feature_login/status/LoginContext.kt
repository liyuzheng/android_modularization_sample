package yz.l.feature_login.status

import androidx.lifecycle.LifecycleOwner
import kotlinx.coroutines.flow.MutableStateFlow
import yz.l.data_user.LoginParams
import yz.l.data_user.LoginResult
import yz.l.data_user.UserMMKV
import yz.l.data_user.UserModel

/**
 * desc:
 * createed by liyuzheng on 2023/8/30 14:10
 */
object LoginContext {
    private var mLoginState: ILoginState = GuestState()
    val loginFlow = MutableStateFlow<UserModel?>(null)
    fun init() {
        if (UserMMKV.currentUser.uid.isNotBlank() &&
            UserMMKV.token.isNotBlank()
        ) {
            login(UserMMKV.currentUser, UserMMKV.token)
        } else {
            logout()
        }
    }

    fun logout() {
        mLoginState = GuestState()
        UserMMKV.currentUser = UserModel()
        UserMMKV.token = ""
        loginFlow.tryEmit(null)
    }

    fun login(userModel: UserModel, token: String) {
        UserMMKV.currentUser = userModel
        UserMMKV.token = token
        mLoginState = LoginState()
        loginFlow.tryEmit(userModel)
    }

    fun action(
        context: LifecycleOwner,
        requestLogin: Boolean = true,
        loginParams: MutableMap<LoginParams, Any> = mutableMapOf(),
        interceptBlock: Boolean = false,
        block: (result: LoginResult) -> Unit
    ) {
        val afterLoginAction = if (interceptBlock) EMPTY_BLOCK else block
        mLoginState.action(context, requestLogin, loginParams, afterLoginAction)
    }

    private val EMPTY_BLOCK = { _: LoginResult -> }
}