package yz.l.feature_login.main

import android.util.SparseArray
import androidx.activity.viewModels
import androidx.lifecycle.ViewModel
import yz.l.core.mvvm.BaseBindingAct
import yz.l.core.mvvm.exts.binding
import yz.l.core_tool.ext.clicks
import yz.l.core_tool.ext.sparse
import yz.l.feature_login.BR
import yz.l.feature_login.R
import yz.l.feature_login.databinding.LoginActBinding

/**
 * desc:
 * createed by liyuzheng on 2023/8/24 18:18
 */
class LoginActivity : BaseBindingAct<LoginActBinding>() {
    override val mBinding by binding<LoginActBinding>(R.layout.login_act)
    private val viewModel by viewModels<LoginViewModel>()
    override fun variables(): SparseArray<ViewModel> {
        return sparse(BR.loginVM to viewModel)
    }

    override fun setupView() {
        super.setupView()
        mBinding.confirm.clicks {
            viewModel.confirm()
        }
    }
}