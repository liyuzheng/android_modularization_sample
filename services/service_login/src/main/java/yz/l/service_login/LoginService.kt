package yz.l.service_login

import android.content.Context
import yz.l.core_router.RouterService

/**
 * desc:
 * createed by liyuzheng on 2023/8/24 17:10
 */
interface LoginService : RouterService {
    fun isLogin(): Boolean
    fun launchLoginActivity(context: Context)
}