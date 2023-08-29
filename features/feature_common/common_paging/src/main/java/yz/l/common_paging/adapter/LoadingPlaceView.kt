package yz.l.common_paging.adapter

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.databinding.BindingAdapter
import androidx.paging.LoadState
import yz.l.common_paging.R
import yz.l.network.ResponseEmpty
import yz.l.network.ResponseException

/**
 * desc:占位加载
 * createed by liyuzheng on 2023/3/29 17:20
 */
class LoadingPlaceView(context: Context, attributeSet: AttributeSet) :
    FrameLayout(context, attributeSet) {
    init {
        View.inflate(context, R.layout.list_loading_place_lay, this)
    }

    fun setLoadState(loadState: LoadState?) {
        if (loadState == null) {
            isVisible = false
            return
        }
        val error = (loadState as? LoadState.Error)?.error
        val realError = (error != null) && error !is ResponseEmpty
        isVisible = loadState is LoadState.Loading || realError
        val loading = findViewById<ProgressBar>(R.id.loading)
        val retry = findViewById<TextView>(R.id.retry)
        val img = findViewById<ImageView>(R.id.img)
        loading.isVisible = loadState is LoadState.Loading
        retry.isVisible = realError
        img.isVisible = realError
        val err = (loadState as? LoadState.Error)?.error
        err?.run {
            when (this) {
                is ResponseEmpty -> {
                    val imgRes = this.icon
                    img.setBackgroundResource(imgRes)
                }

                is ResponseException -> {
                    //todo
                }

                else -> {
                    //todo
                }
            }
        }

    }

}

@BindingAdapter("bindLoadingPlaceLoadingView")
fun bindLoadingPlaceLoadingView(view: LoadingPlaceView, loadState: LoadState?) {
    view.setLoadState(loadState)
}
