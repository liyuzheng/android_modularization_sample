package yz.l.fm

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import yz.l.data_user.TestParcelableModel
import yz.l.data_user.UserMMKV
import yz.l.data_user.UserModel

/**
 * desc:
 * createed by liyuzheng on 2023/8/24 17:59
 */
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_act)
        Log.v("ssssss", "user ${UserMMKV.test}")
        UserMMKV.test = "5678"
        Log.v("ssssss", "user${UserMMKV.user}")
        UserMMKV.user = UserModel("33333", "222222", 2)

        Log.v("ssssss", "test ${UserMMKV.testUser.n}")
        UserMMKV.testUser = TestParcelableModel("99999")
    }
}