package yz.l.feature_lottery

import androidx.databinding.BindingAdapter
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asFlow
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import yz.l.common_paging.ext.request
import yz.l.core.mvvm.BaseViewModel
import yz.l.data.lottery.LotteryDB
import yz.l.data.lottery.LotteryModel
import yz.l.data.lottery.toLotteryModel

/**
 * desc:
 * createed by liyuzheng on 2023/8/29 15:19
 */
class LotteriesViewModel : BaseViewModel() {
    val loadingObs = MutableLiveData(false)

    val searchObs = MutableLiveData("")

    @OptIn(ExperimentalCoroutinesApi::class)
    val posts = searchObs.asFlow().flatMapLatest {
        val page = it.ifBlank { "1" }
        val api = "https://liyuzheng.github.io/bigfile.io/lottery/shuangseqiu$page.html"
        fetch(api)
    }.cachedIn(viewModelScope)


    private suspend fun fetch(queryStr: String): Flow<PagingData<LotteryModel>> {
        val mediator = LotteryMediator(queryStr)
        return mediator.request(viewModel = this, fetchData = {
            LotteryDB.getLotteryPagingSource(mediator.remoteName)
        }, transform = {
            it.toLotteryModel()
        })
    }
}

@BindingAdapter("bindLoadState")
fun bindLoadState(view: SwipeRefreshLayout, loading: Boolean?) {
    view.isRefreshing = loading == true
}