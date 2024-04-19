package yz.l.fm

import android.util.SparseArray
import androidx.activity.viewModels
import androidx.lifecycle.ViewModel
import yz.l.core.mvvm.BaseBindingAct
import yz.l.core.mvvm.exts.binding
import yz.l.core_tool.ext.getPlatformProxy
import yz.l.core_tool.ext.sparse
import yz.l.fm.databinding.MainActBinding
import yz.l.fm.platformstrategy.ILogPlatformAction
import yz.l.service.lottery.LotteryProxy

/**
 * desc:
 * createed by liyuzheng on 2023/8/24 17:59
 */
class MainActivity : BaseBindingAct<MainActBinding>() {
    override val mBinding by binding<MainActBinding>(R.layout.main_act)
    override var onBackPressedCallbackEnable = true //允许截获backPress
    private val mViewModel by viewModels<MainActViewModel>()

    override fun variables(): SparseArray<ViewModel> {
        return sparse(BR.mainVM to mViewModel)
    }

    private val logProxy by lazy {
        getPlatformProxy<ILogPlatformAction>()
    }

    override fun setup() {
        super.setup()
        LotteryProxy.launchLotteriesActAuto(this)
    }
}