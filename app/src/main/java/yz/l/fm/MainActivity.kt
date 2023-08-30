package yz.l.fm

import android.util.SparseArray
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import yz.l.core.mvvm.BaseBindingAct
import yz.l.core.mvvm.exts.binding
import yz.l.core_tool.ext.clicks
import yz.l.core_tool.ext.sparse
import yz.l.data_user.onCancel
import yz.l.data_user.onGuest
import yz.l.data_user.onSuccess
import yz.l.feature_login.proxy.requestLogin
import yz.l.fm.databinding.MainActBinding
import yz.l.service_login.LoginProxy

/**
 * desc:
 * createed by liyuzheng on 2023/8/24 17:59
 */
class MainActivity : BaseBindingAct<MainActBinding>() {
    override val mBinding by binding<MainActBinding>(R.layout.main_act)
    private val mViewModel by viewModels<MainActViewModel>()

    override fun variables(): SparseArray<ViewModel> {
        return sparse(BR.mainVM to mViewModel)
    }

    override fun setup() {
        super.setup()
        setupObs()
    }

    override fun onBackPressedCallback() {
        super.onBackPressedCallback()
        moveTaskToBack(false)
    }

    override fun setupView() {
        super.setupView()
        setupLogoutBtn()
        setupTestGuestBtn()
    }

    override fun setupData() {
        super.setupData()
        LoginProxy.action(this, false) {
            it.onSuccess {
                mViewModel.confirmBtnTextObs.value = "注销"
            }
            it.onGuest {
                mViewModel.confirmBtnTextObs.value = "登录"
            }
        }
    }

    private fun setupLogoutBtn() {
        mBinding.logout.clicks {
            LoginProxy.action(this, false) {
                it.onSuccess {
                    LoginProxy.logout()
                }
                it.onGuest {
                    requestLogin()
                }
            }
        }
    }

    private fun requestLogin() {
        LoginProxy.action(this) {
            it.onSuccess {
                Toast.makeText(this, "登录成功", Toast.LENGTH_SHORT).show()
            }
            it.onCancel {
                Toast.makeText(this, "取消登录", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun setupTestGuestBtn() {
        mBinding.testGuest.clicks {
            LoginProxy.action(this, false) {
                it.onSuccess {
                    Toast.makeText(this, "登录状态点击", Toast.LENGTH_SHORT).show()
                }
                it.onGuest {
                    Toast.makeText(this, "游客状态点击", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override var onBackPressedCallbackEnable = true

    private fun setupObs() {
        LoginProxy.getLoginStateFlow().onEach { userModel ->
            mViewModel.confirmBtnTextObs.value = if (userModel == null) "登录" else "注销"
        }.launchIn(lifecycleScope).start()
    }

    private fun test() {
        requestLogin {
            it.onSuccess {
                //登录成功后执行~
            }
        }
    }
}