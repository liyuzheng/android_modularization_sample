package yz.l.service_login

import androidx.lifecycle.LifecycleOwner
import yz.l.core_router.Router
import yz.l.data_user.LoginParams
import yz.l.data_user.LoginResult

/**
 * desc:
 * createed by liyuzheng on 2023/8/24 17:31
 */
object LoginProxy {
    private val provider by lazy {
        Router.getProvider(LoginServicePath.LOGIN_SERVICE_PATH_CODE) as LoginService
    }

    fun action(
        context: LifecycleOwner,
        requestLogin: Boolean = true,
        loginParams: MutableMap<LoginParams, Any> = mutableMapOf(),
        interceptBlock: Boolean = false,
        block: (result: LoginResult) -> Unit
    ) = provider.action(context, requestLogin, loginParams, interceptBlock, block)


    fun getLoginStateFlow() = provider.getLoginStateObs()

    fun logout() = provider.logout()

}