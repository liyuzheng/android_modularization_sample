package yz.l.feature_login.provider

import android.content.Context
import android.content.Intent
import androidx.lifecycle.LifecycleOwner
import yz.l.core_router.Router
import yz.l.data_user.LoginParams
import yz.l.data_user.LoginResult
import yz.l.feature_login.main.LoginActivity
import yz.l.feature_login.status.LoginContext
import yz.l.service_login.LoginServicePath

/**
 * desc:
 * createed by liyuzheng on 2023/8/24 17:22
 */
class LoginProvider : yz.l.service_login.LoginService {
    override fun init(app: Context) {
        Router.register(LoginServicePath.LOGIN_SERVICE_PATH_CODE, this)
    }

    override fun launchLoginActivity(context: Context) {
        context.startActivity(Intent(context, LoginActivity::class.java))
    }

    override fun action(
        context: LifecycleOwner,
        requestLogin: Boolean,
        loginParams: MutableMap<LoginParams, Any>,
        interceptBlock: Boolean,
        block: (result: LoginResult) -> Unit
    ) {
        LoginContext.action(context, requestLogin, loginParams, interceptBlock, block)
    }

    override fun getLoginStateObs() = LoginContext.loginFlow
    override fun logout() {
        LoginContext.logout()
    }


}