package yz.l.feature_login.status

import androidx.lifecycle.LifecycleOwner
import yz.l.data_user.LoginParams
import yz.l.data_user.LoginResult
import yz.l.feature_login.proxy.requestLogin

/**
 * desc:
 * createed by liyuzheng on 2023/8/30 14:10
 */
class GuestState : ILoginState {
    override fun action(
        context: LifecycleOwner,
        requestLogin: Boolean,
        loginParams: MutableMap<LoginParams, Any>,
        block: (result: LoginResult) -> Unit
    ) {
        if (requestLogin) {
            context.requestLogin(loginParams, block)
        } else {
            block(LoginResult.Guest())
        }
    }
}