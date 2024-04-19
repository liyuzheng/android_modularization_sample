package yz.l.feature.diary.list

import yz.l.core.mvvm.BaseBindingAct
import yz.l.core.mvvm.exts.binding
import yz.l.feature.diary.R
import yz.l.feature.diary.databinding.DiaryListActBinding

/**
 * desc:
 * created by liyuzheng on 2023/9/4 17:49
 */
class DiaryListAct : BaseBindingAct<DiaryListActBinding>() {
    override val mBinding by binding<DiaryListActBinding>(R.layout.diary_list_act)

}