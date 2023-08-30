package yz.l.feature_login.proxy

import android.content.Intent
import android.os.Parcelable
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.commit
import androidx.lifecycle.LifecycleOwner
import yz.l.core_tool.ext.launchWhenResumed
import yz.l.data_user.LoginParams
import yz.l.data_user.LoginResult
import yz.l.feature_login.main.LoginActivity
import yz.l.feature_login.main.LoginActivity.Companion.K_LOGIN_RESULT
import yz.l.feature_login.main.LoginActivity.Companion.R_LOGIN_CANCEL
import yz.l.feature_login.main.LoginActivity.Companion.R_LOGIN_SUCCESS
import yz.l.feature_login.main.LoginActivity.Companion.R_LOGIN_UNKNOWN
import java.io.Serializable

/**
 * desc:
 * createed by liyuzheng on 2023/8/30 14:39
 */

fun LifecycleOwner.requestLogin(
    loginParams: MutableMap<LoginParams, Any> = mutableMapOf(),
    block: (result: LoginResult) -> Unit
) {
    runCatching {
        when (this@requestLogin) {
            is FragmentActivity -> {
                LoginProxyFragment.getLoginProxyFragment(this@requestLogin, block)
                    .requestLogin(loginParams)
            }

            is Fragment -> {
                LoginProxyFragment.getLoginProxyFragment(this@requestLogin.requireActivity(), block)
                    .requestLogin(loginParams)
            }

            else -> {
                throw RuntimeException("requestPermission LifecycleOwner必须是activity或fragment")
            }
        }
    }.onFailure {
        block(LoginResult.Cancel())
    }
}


class LoginProxyFragment : Fragment() {
    companion object {
        private const val TAG = "LoginProxyFragmentTag"
        fun getLoginProxyFragment(
            activity: FragmentActivity,
            block: (result: LoginResult) -> Unit
        ): LoginProxyFragment {
            var loginProxyFragment = findLoginProxyFragment(activity)
            if (loginProxyFragment == null) {
                loginProxyFragment = LoginProxyFragment()
                activity.supportFragmentManager.commit(true) {
                    add(loginProxyFragment, TAG)
                }
            }
            loginProxyFragment.block = block
            return loginProxyFragment
        }

        private fun findLoginProxyFragment(activity: FragmentActivity): LoginProxyFragment? {
            return activity.supportFragmentManager.findFragmentByTag(TAG) as? LoginProxyFragment
        }
    }

    private var block: (result: LoginResult) -> Unit = { }

    private val launcher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        when (it.data?.getStringExtra(K_LOGIN_RESULT) ?: R_LOGIN_UNKNOWN) {
            R_LOGIN_SUCCESS -> {
                block(LoginResult.Success())
            }

            R_LOGIN_CANCEL -> {
                block(LoginResult.Cancel())
            }

            else -> {
                block(LoginResult.UnKnown())
            }
        }
    }

    fun requestLogin(loginParams: MutableMap<LoginParams, Any> = mutableMapOf()) {
        launchWhenResumed {
            launcher.launch(Intent(requireActivity(), LoginActivity::class.java).apply {
                loginParams.forEach { (k, v) ->
                    when (v) {
                        is String -> putExtra(k.key, v)
                        is Boolean -> putExtra(k.key, v)
                        is Int -> putExtra(k.key, v)
                        is Float -> putExtra(k.key, v)
                        is Long -> putExtra(k.key, v)
                        is Parcelable -> putExtra(k.key, v)
                        is Serializable -> putExtra(k.key, v)
                        else -> throw IllegalArgumentException("loginParams 不支持的类型")
                    }
                }
            })
        }
    }

}

