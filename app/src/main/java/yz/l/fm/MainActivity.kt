package yz.l.fm

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.asLiveData
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import yz.l.data_user.UserDB
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

        //常规flow监听
        lifecycleScope.launchWhenResumed {
            UserDB.getUserFlow("test1").collect {
                Log.v("collect", "collect: $it")
            }
        }

        //常规flow操作符监听
        UserDB.getUserFlow("test1")
//            .catch {  }//错误捕获
            .onEach {
                Log.v("launchIn", "launchIn: $it")
            }
//            .catch {  } //onEach中的错误捕获
            .launchIn(lifecycleScope).start()

        //转化为liveData后的监听 private val userModelObs = UserDB.getUserFlow("test1").asLiveData()
        userModelObs.observe(this) {
            Log.v("livedata", "livedata: $it")
        }

        //修改数据
        lifecycleScope.launchWhenResumed {
            UserDB.updateUserAsync(UserModel("test1", "test5", 5))
        }
    }
}