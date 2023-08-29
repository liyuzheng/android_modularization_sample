package yz.l.common_paging.adapter

import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.recyclerview.widget.RecyclerView
import yz.l.common_paging.R

class NetworkStateItemViewHolder(view: View, private val retryCallback: () -> Unit) :
    RecyclerView.ViewHolder(view) {
    fun bindData(data: LoadState, ignore: Boolean) {
//        val txt = itemView.findViewById<TextView>(R.id.error_msg)
        val progressBar = itemView.findViewById<ProgressBar>(R.id.progress_bar)
        val retry = itemView.findViewById<TextView>(R.id.retry_button)
        progressBar.isVisible = data is LoadState.Loading && !ignore
//        txt.isVisible = data is LoadState.Error && !ignore
        retry.isVisible = data is LoadState.Error && !ignore
        retry.isFocusable = false
        progressBar.isFocusable = false
//        txt.isFocusable = false
//        if (data is LoadState.Error) {
//            txt.text = data.error.message
//        }
    }
}