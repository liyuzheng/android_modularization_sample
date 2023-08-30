package yz.l.feature_login.status

import androidx.lifecycle.LifecycleOwner
import yz.l.data_user.LoginParams
import yz.l.data_user.LoginResult

/**
 * desc:
 * createed by liyuzheng on 2023/8/30 14:10
 */
class LoginState : ILoginState {
    override fun action(
        context: LifecycleOwner,
        requestLogin: Boolean,
        loginParams: MutableMap<LoginParams, Any>,
        block: (result: LoginResult) -> Unit
    ) {
        block(LoginResult.Success())
    }

}