package yz.l.common_paging.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.paging.PagingDataAdapter
import yz.l.common_paging.common.PAGE_SIZE

/**
 * desc:
 * createed by liyuzheng on 2023/3/31 17:48
 */
abstract class FooterAdapter(
    private val adapter: PagingDataAdapter<*, *>,
    private val ignoreProgressCount: Int = PAGE_SIZE
) : LoadStateAdapter<NetworkStateItemViewHolder>() {
    companion object {
        const val VIEW_TYPE = -1
    }

    init {
        adapter.addLoadStateListener {
            it.mediator?.append?.run {
                loadState = this
            }
        }
    }

    override fun onBindViewHolder(holder: NetworkStateItemViewHolder, loadState: LoadState) {
        holder.bindData(loadState, adapter.itemCount <= ignoreProgressCount)
    }

    override fun getStateViewType(loadState: LoadState): Int {
        return VIEW_TYPE
    }

    override fun displayLoadStateAsItem(loadState: LoadState): Boolean {
        return true
    }

    protected fun inflateView(viewGroup: ViewGroup, @LayoutRes viewType: Int): View {
        val layoutInflater = LayoutInflater.from(viewGroup.context)
        return layoutInflater.inflate(viewType, viewGroup, false)
    }
}