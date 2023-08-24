package yz.l.feature_login.provider

import android.content.Context
import android.content.Intent
import yz.l.core_router.Router
import yz.l.feature_login.main.LoginActivity
import yz.l.service_login.LoginServicePath

/**
 * desc:
 * createed by liyuzheng on 2023/8/24 17:22
 */
class LoginProvider : yz.l.service_login.LoginService {
    override fun init(app: Context) {
        Router.register(LoginServicePath.LOGIN_SERVICE_PATH_CODE, this)
    }

    override fun isLogin(): Boolean {
        return true
    }

    override fun launchLoginActivity(context: Context) {
        context.startActivity(Intent(context, LoginActivity::class.java))
    }
}