package yz.l.data_user

import com.tencent.mmkv.MMKV
import yz.l.core_tool.MMKVReadWrite

/**
 * desc:
 * createed by liyuzheng on 2023/8/25 13:24
 */
object UserMMKV {
    private val mmkv by lazy {
        MMKV.mmkvWithID("UserStore")
    }

    var currentUser by UserMMKV("login.user", UserModel())
    var token by UserMMKV("login.token", "")

    class UserMMKV<T>(key: String, defaultValue: T) : MMKVReadWrite<T>(key, defaultValue) {
        override fun getMMKV(): MMKV = mmkv
    }
}