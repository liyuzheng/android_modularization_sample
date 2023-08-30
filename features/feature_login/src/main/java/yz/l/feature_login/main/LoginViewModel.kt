package yz.l.feature_login.main

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import yz.l.core.mvvm.BaseViewModel
import yz.l.network.StringBaseResponse
import yz.l.network.ext.repo
import yz.l.network.ext.request
import yz.l.network.onFailure
import yz.l.network.onLoading
import yz.l.network.onSuccess

/**
 * desc:
 * createed by liyuzheng on 2023/8/28 18:26
 */
class LoginViewModel : BaseViewModel() {
    val titleObs = MutableLiveData("")
    val showCloseBtn = MutableLiveData(true)


    val userName = MutableLiveData("")
    val passWord = MutableLiveData("")

    fun confirm() {
        repo {
            api { "https://www.baidu.com" }
            params { "user_name" to userName.value }
            params { "password" to passWord.value }
        }.request<StringBaseResponse>(viewModelScope) { result ->
            result.onLoading {
                Log.v("ssssss", "loading $it")
            }
            result.onSuccess {
                Log.v("ssssss", "success")
            }
            result.onFailure {
                Log.v("ssssss", "failure $it")
            }
        }
    }
}