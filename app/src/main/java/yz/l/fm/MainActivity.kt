package yz.l.fm

import android.Manifest
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import yz.l.core_tool.ext.requestPermission

/**
 * desc:
 * createed by liyuzheng on 2023/8/24 17:59
 */
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_act)
        requestPermission(Manifest.permission.READ_PHONE_STATE) { g, d, ad ->
            Log.v("granted", "WRITE_EXTERNAL_STORAGE is granted $g")
        }

        requestPermission(
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.READ_CONTACTS
        ) { g, d, ad ->
            Log.v("granted", "WRITE_EXTERNAL_STORAGE is granted $g")
        }
    }
}