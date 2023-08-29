package yz.l.common_paging.adapter

import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.paging.PagingDataAdapter
import yz.l.common_paging.R
import yz.l.common_paging.common.PAGE_SIZE

/**
 * desc:横向的list loading
 * createed by liyuzheng on 2023/3/31 17:47
 */
class HorizontalFooterAdapter(
    private val adapter: PagingDataAdapter<*, *>,
    private val width: Int = 0,
    ignoreProgressCount: Int = PAGE_SIZE
) : FooterAdapter(adapter, ignoreProgressCount) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        loadState: LoadState
    ): NetworkStateItemViewHolder {
        val view = inflateView(parent, R.layout.horizontal_footer_lay)
        if (width != 0) {
            view.layoutParams.height = width
        }
        return NetworkStateItemViewHolder(view) {
            adapter.retry()
        }
    }
}