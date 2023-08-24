package yz.l.fm

import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import yz.l.service_login.LoginProxy

/**
 * desc:
 * createed by liyuzheng on 2023/8/24 17:59
 */
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_act)
        Toast.makeText(this, "login status is ${LoginProxy.isLogin()}", Toast.LENGTH_SHORT).show()
        val btn = findViewById<TextView>(R.id.test)
        btn.setOnClickListener {
            LoginProxy.launchLoginActivity(this@MainActivity)
        }
    }
}