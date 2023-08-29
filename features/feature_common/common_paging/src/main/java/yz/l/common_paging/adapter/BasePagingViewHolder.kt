package yz.l.common_paging.adapter

import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView

/**
 * desc:
 * createed by liyuzheng on 2023/3/30 15:59
 */
abstract class BasePagingViewHolder<M, T : ViewDataBinding>(protected val mBinding: T) :
    RecyclerView.ViewHolder(mBinding.root) {
    open fun setup() {}

    abstract fun bind(data: M)

    open fun recycle() {
    }

    inline fun <reified T : ViewDataBinding> viewHolderBinding(view: View): Lazy<T> =
        lazy {
            requireNotNull(DataBindingUtil.bind(view)) { "cannot find the matched layout." }
        }

}