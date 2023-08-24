package yz.l.fm

import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import yz.l.network.RequestMode
import yz.l.network.StringBaseResponse
import yz.l.network.ext.repo
import yz.l.network.ext.request
import yz.l.network.onCancel
import yz.l.network.onCompletion
import yz.l.network.onFailure
import yz.l.network.onSuccess
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
        repo {
            api { "https://www.baidu.com/" }
            params { "a" to "b" }
            params { "c" to "d" }
            requestMode { RequestMode.GET }
        }.request<StringBaseResponse>(lifecycleScope) { result ->
            //以下方法不使用可忽略
            result.onSuccess {
                //do something
            }
            result.onCancel {
                //do something
            }
            result.onCompletion {
                //do something
            }
            result.onFailure {
                //do something
            }
        }

        val repo1 = repo {
            api { "https://www.baidu.com/" }
            params { "a" to "b" }
            params { "c" to "d" }
            requestMode { RequestMode.GET }
        }
        val repo2 = repo {
            api { "https://www.github.com/" }
            params { "a" to "b" }
            params { "c" to "d" }
            requestMode { RequestMode.GET }
        }
        lifecycleScope.request<StringBaseResponse, StringBaseResponse>(repo1, repo2) { result ->
            //以下方法不使用可忽略
            result.onSuccess {
                val r1 = it.first
                val r2 = it.second
                //do something
            }
            result.onCancel {
                //do something
            }
            result.onCompletion {
                //do something
            }
            result.onFailure {
                //do something
            }
        }

        //最简单的get请求
        repo {
            api { "https://www.baidu.com/" }
        }.request<StringBaseResponse>(lifecycleScope) { result ->
            //以下方法不使用可忽略
            result.onSuccess {
                //do something
            }
            result.onFailure {
                //do something
            }
        }
    }
}