package yz.l.common_paging.adapter

import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil

/**
 * desc:
 * createed by liyuzheng on 2023/3/30 15:56
 */
abstract class BasePagingAdapter<T : Any, VH : BasePagingViewHolder<T, *>>(
    diffCallback: DiffUtil.ItemCallback<T>
) : PagingDataAdapter<T, VH>(diffCallback) {

    open fun onBindViewHolder(holder: VH, item: T) {
        holder.bind(item)
    }

    open fun getItemViewType(position: Int, data: T): Int = 0

    final override fun onBindViewHolder(holder: VH, position: Int) {
        holder.setup()
        onBindViewHolder(holder, getItem(position) ?: return)
    }

    final override fun getItemViewType(position: Int): Int {
        val data = getItem(position) ?: return super.getItemViewType(position)
        return getItemViewType(position, data)
    }

    override fun onViewRecycled(holder: VH) {
        super.onViewRecycled(holder)
        holder.recycle()
    }

}