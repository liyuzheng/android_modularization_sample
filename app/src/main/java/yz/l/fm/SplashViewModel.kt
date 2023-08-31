package yz.l.fm

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import yz.l.core.mvvm.BaseViewModel

/**
 * desc:
 * createed by liyuzheng on 2023/8/31 14:04
 */
class SplashViewModel : BaseViewModel() {
    var isReady = false

    fun init() {
        viewModelScope.launch {
            //do something init
            //模拟初始化
            delay(5000)
            isReady = true
        }
    }
}