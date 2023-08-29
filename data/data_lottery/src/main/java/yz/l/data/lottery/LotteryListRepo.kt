package yz.l.data.lottery

import androidx.lifecycle.ViewModel
import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import yz.l.common_paging.ext.request

/**
 * desc:
 * createed by liyuzheng on 2023/8/29 15:08
 */
object LotteryListRepo {
    suspend fun getPagingFlow(
        viewModel: ViewModel,
        queryStr: String = ""
    ): Flow<PagingData<LotteryModel>> {
        val mediator = LotteryMediator(queryStr)
        return mediator.request(
            viewModel = viewModel,
            fetchData = {
                LotteryDB.getLotteryPagingSource(mediator.remoteName)
            },
            transform = {
                it.toLotteryModel()
            })
    }
}