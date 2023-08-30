package yz.l.service_login

import android.content.Context
import androidx.lifecycle.LifecycleOwner
import kotlinx.coroutines.flow.StateFlow
import yz.l.core_router.RouterService
import yz.l.data_user.LoginParams
import yz.l.data_user.LoginResult
import yz.l.data_user.UserModel

/**
 * desc:
 * createed by liyuzheng on 2023/8/24 17:10
 */
interface LoginService : RouterService {
    fun launchLoginActivity(context: Context)

    fun action(
        context: LifecycleOwner,
        requestLogin: Boolean,
        loginParams: MutableMap<LoginParams, Any> = mutableMapOf(),
        interceptBlock: Boolean = false,
        block: (result: LoginResult) -> Unit
    )

    fun getLoginStateObs(): StateFlow<UserModel?>

    fun logout()
}