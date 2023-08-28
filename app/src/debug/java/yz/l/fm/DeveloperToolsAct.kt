package yz.l.fm

import yz.l.core.mvvm.BaseBindingAct
import yz.l.core.mvvm.exts.binding
import yz.l.core_tool.ext.clicks
import yz.l.fm.databinding.DeveloperToolsActBinding

/**
 * desc:
 * createed by liyuzheng on 2023/8/28 20:36
 */
class DeveloperToolsAct : BaseBindingAct<DeveloperToolsActBinding>() {
    override val mBinding by binding<DeveloperToolsActBinding>(R.layout.developer_tools_act)
    override fun setupView() {
        super.setupView()
        mBinding.title.clicks {

        }
    }

    companion object {

    }
}