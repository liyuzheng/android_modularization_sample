package yz.l.fm

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.asLiveData
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import yz.l.data_user.UserDB
import yz.l.data_user.UserMMKV
import yz.l.data_user.UserModel

/**
 * desc:
 * createed by liyuzheng on 2023/8/24 17:59
 */
class MainActivity : AppCompatActivity() {

    private val userModelObs = UserDB.getUserFlow("test1").asLiveData()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_act)
        Log.v("ssssss", "user${UserMMKV.user}")
    }
}