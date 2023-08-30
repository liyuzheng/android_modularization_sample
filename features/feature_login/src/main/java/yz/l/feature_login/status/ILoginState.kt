package yz.l.feature_login.status

import androidx.lifecycle.LifecycleOwner
import yz.l.data_user.LoginParams
import yz.l.data_user.LoginResult

/**
 * desc:
 * createed by liyuzheng on 2023/8/30 14:10
 */
interface ILoginState {
    /**
     * @param context
     * @param requestLogin 当游客状态时，执行某个动作是否需要呼起登录弹框
     * @param loginParams 登录参数，传入loginActivity
     * @param block 执行结果回调
     */
    fun action(
        context: LifecycleOwner,
        requestLogin: Boolean,
        loginParams: MutableMap<LoginParams, Any>,
        block: (result: LoginResult) -> Unit
    )
}