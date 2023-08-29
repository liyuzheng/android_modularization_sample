package yz.l.common_paging.adapter

import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.paging.PagingDataAdapter
import yz.l.common_paging.R
import yz.l.common_paging.common.PAGE_SIZE

/**
 * desc:纵向list loading
 * createed by liyuzheng on 2021/1/18 14:07
 */
class VerticalFooterAdapter(
    private val adapter: PagingDataAdapter<*, *>,
    private val height: Int = 0,
    ignoreProgressCount: Int = PAGE_SIZE
) : FooterAdapter(adapter, ignoreProgressCount) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        loadState: LoadState
    ): NetworkStateItemViewHolder {
        val view = inflateView(parent, R.layout.vertical_footer_lay)
        if (height != 0) {
            view.layoutParams.height = height
        }
        return NetworkStateItemViewHolder(view) {
            adapter.retry()
        }
    }
}