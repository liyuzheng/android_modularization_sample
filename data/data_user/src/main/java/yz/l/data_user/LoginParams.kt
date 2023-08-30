package yz.l.data_user


/**
 * desc:
 * createed by liyuzheng on 2023/8/30 15:18
 */

const val K_LOGIN_TITLE = "title"
const val K_LOGIN_SHOW_CLOSE_BTN = "showCloseBtn"

enum class LoginParams(val key: String) {
    LOGIN_TITLE(K_LOGIN_TITLE),
    LOGIN_CLOSE_BTN_SHOW(K_LOGIN_SHOW_CLOSE_BTN)
}