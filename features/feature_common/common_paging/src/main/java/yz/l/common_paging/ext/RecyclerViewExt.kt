package yz.l.common_paging.ext

import androidx.recyclerview.widget.RecyclerView

/**
 * desc:
 * createed by liyuzheng on 2023/3/30 19:56
 */


fun RecyclerView.refreshAdapter() {
    val temp = this.adapter
    this.adapter = null
    this.adapter = temp
}

