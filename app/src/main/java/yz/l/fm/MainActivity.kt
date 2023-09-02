package yz.l.fm

import android.util.SparseArray
import androidx.activity.viewModels
import androidx.lifecycle.ViewModel
import yz.l.common.view.navbottomview.BottomNavigationGroup
import yz.l.common.view.navbottomview.bottomNavOption
import yz.l.common.view.navbottomview.setup
import yz.l.core.mvvm.BaseBindingAct
import yz.l.core.mvvm.exts.binding
import yz.l.core_tool.ext.sparse
import yz.l.fm.databinding.MainActBinding

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

    override fun setup() {
        super.setup()
        mBinding.homeTab.setup {
            options(
                bottomNavOption {
                    id { R.id.home }
                    tabText { "home" }
                    iconRes { R.drawable.ic_main_nav_home }
                })
            options(bottomNavOption {
                id { R.id.topic }
                tabText { "topic" }
                iconRes { R.drawable.ic_main_nav_home }
            })
            options(
                bottomNavOption {
                    id { R.id.find }
                    tabText { "find" }
                    iconRes { R.drawable.ic_main_nav_home }
                })
            bottomNavOption {
                id { R.id.me }
                tabText { "me" }
                iconRes { R.drawable.ic_main_nav_home }
            }
            )
            listener {
                object : BottomNavigationGroup.OnCheckedChangeListener {
                    override fun onCheckedChanged(group: BottomNavigationGroup?, checkedId: Int) {

                    }

                }
            }
            defaultChecked {
                R.id.home
            }
        }
        mBinding.homeTab.setBadge(R.id.topic, 12)
    }


}