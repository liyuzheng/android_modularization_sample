package yz.l.service_login

import android.content.Context
import yz.l.core_router.Router

/**
 * desc:
 * createed by liyuzheng on 2023/8/24 17:31
 */
object LoginProxy {
    private val provider by lazy {
        Router.getProvider(LoginServicePath.LOGIN_SERVICE_PATH_CODE) as LoginService
    }

    fun isLogin(): Boolean {
        return provider.isLogin()
    }

    fun launchLoginActivity(context: Context) {
        provider.launchLoginActivity(context)
    }
}