package yz.l.feature_lottery

import android.util.Log
import androidx.databinding.BindingAdapter
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asFlow
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flatMapLatest
import yz.l.core.mvvm.BaseViewModel
import yz.l.data.lottery.LotteryListRepo

/**
 * desc:
 * createed by liyuzheng on 2023/8/29 15:19
 */
class LotteriesViewModel : BaseViewModel() {
    val loadingObs = MutableLiveData(false)

    val searchObs = MutableLiveData("")

    @OptIn(ExperimentalCoroutinesApi::class)
    val posts = searchObs.asFlow()
        .flatMapLatest {
            val page = it.ifBlank { "1" }
            val api = "https://liyuzheng.github.io/bigfile.io/lottery/shuangseqiu$page.html"
            fetch(api)
        }.cachedIn(viewModelScope)


    private suspend fun fetch(queryStr: String) = LotteryListRepo.getPagingFlow(this, queryStr)
}

@BindingAdapter("bindLoadState")
fun bindLoadState(view: SwipeRefreshLayout, loading: Boolean?) {
    view.isRefreshing = loading == true
}