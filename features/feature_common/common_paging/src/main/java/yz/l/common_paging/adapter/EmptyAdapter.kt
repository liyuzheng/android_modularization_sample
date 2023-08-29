package yz.l.common_paging.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.LayoutRes
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import yz.l.common_paging.R

/**
 * desc:
 * createed by liyuzheng on 2023/3/29 15:29
 */
class EmptyAdapter(
    val adapter: BasePagingAdapter<*, *>,
    private val width: Int,
    private val height: Int = 0
) : LoadStateAdapter<EmptyAdapter.EmptyViewHolder>() {

    init {
        adapter.addLoadStateListener {
            it.mediator?.refresh?.run {
                loadState = this
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        loadState: LoadState
    ): EmptyViewHolder {
        val view = inflateView(parent, R.layout.recycie_item_base_empty_state)
        if (height > 0)
            view.layoutParams?.height = height
        view.layoutParams?.width = width
        return EmptyViewHolder(view) {
            adapter.refresh()
        }
    }

    private fun inflateView(viewGroup: ViewGroup, @LayoutRes viewType: Int): View {
        val layoutInflater = LayoutInflater.from(viewGroup.context)
        return layoutInflater.inflate(viewType, viewGroup, false)
    }

    override fun onBindViewHolder(holder: EmptyViewHolder, loadState: LoadState) {
        holder.bindData(loadState, 0)
    }

    override fun displayLoadStateAsItem(loadState: LoadState): Boolean {
        return loadState is LoadState.Error
    }


    class EmptyViewHolder(view: View, private val retryCallback: () -> Unit) :
        RecyclerView.ViewHolder(view) {

        fun bindData(data: LoadState, position: Int) {
            val txt = itemView.findViewById<TextView>(R.id.mes)
            val desc = itemView.findViewById<TextView>(R.id.desc)
            if (data is LoadState.Error) {
                txt.text = data.error.message
                desc.post {
                    desc.requestFocus()
                }
            }
            desc.setOnClickListener {
                retryCallback()
            }

        }

        private fun setDesc(icon: Int, mes: String) {

        }
    }
}
