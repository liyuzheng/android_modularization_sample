package yz.l.fm

import androidx.lifecycle.MutableLiveData
import yz.l.core.mvvm.BaseViewModel

/**
 * desc:
 * createed by liyuzheng on 2023/8/30 16:33
 */
class MainActViewModel : BaseViewModel() {
    val confirmBtnTextObs = MutableLiveData("登录")
}