package yz.l.feature_login.main

import android.content.Intent
import android.util.SparseArray
import androidx.activity.viewModels
import androidx.lifecycle.ViewModel
import yz.l.core.mvvm.BaseBindingAct
import yz.l.core.mvvm.exts.binding
import yz.l.core_tool.ext.clicks
import yz.l.core_tool.ext.sparse
import yz.l.data_user.K_LOGIN_SHOW_CLOSE_BTN
import yz.l.data_user.K_LOGIN_TITLE
import yz.l.data_user.UserModel
import yz.l.feature_login.BR
import yz.l.feature_login.R
import yz.l.feature_login.databinding.LoginActBinding
import yz.l.feature_login.status.LoginContext

/**
 * desc:
 * createed by liyuzheng on 2023/8/24 18:18
 */
class LoginActivity : BaseBindingAct<LoginActBinding>() {
    companion object {
        const val K_TITLE = K_LOGIN_TITLE
        const val K_SHOW_CLOSE_BTN = K_LOGIN_SHOW_CLOSE_BTN
        const val K_LOGIN_RESULT = "loginResult"
        const val R_LOGIN_SUCCESS = "success"
        const val R_LOGIN_CANCEL = "cancel"
        const val R_LOGIN_UNKNOWN = "unknown"
    }

    override val mBinding by binding<LoginActBinding>(R.layout.login_act)
    private val viewModel by viewModels<LoginViewModel>()
    override fun variables(): SparseArray<ViewModel> {
        return sparse(BR.loginVM to viewModel)
    }

    override fun setupData() {
        super.setupData()
        viewModel.titleObs.value = intent.getStringExtra(K_TITLE) ?: "默认登录提示文案"
        viewModel.showCloseBtn.value = intent.getBooleanExtra(K_SHOW_CLOSE_BTN, true)
    }

    override fun setupView() {
        super.setupView()
        setupConfirm()
        setupClose()
    }

    private fun setupConfirm() {
        mBinding.confirm.clicks {
            val testLoginUserModel = UserModel("1234", "testuser")
            LoginContext.login(testLoginUserModel, "testToken")
            setResult(RESULT_OK, Intent().apply {
                putExtra(K_LOGIN_RESULT, R_LOGIN_SUCCESS)
            })
            finish()
        }
    }

    private fun setupClose() {
        mBinding.closeBtn.clicks {
            setResult(RESULT_OK, Intent().apply {
                putExtra(K_LOGIN_RESULT, R_LOGIN_CANCEL)
            })
            finish()
        }
    }
}