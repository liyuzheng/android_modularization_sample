package yz.l.common_paging.ext

import androidx.lifecycle.ViewModel
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.PagingSource
import kotlinx.coroutines.flow.Flow
import yz.l.common_paging.common.BaseRemoteMediator
import yz.l.common_paging.common.DEFAULT_PAGER

/**
 * desc:
 * createed by liyuzheng on 2023/3/10 10:15
 */

/**
 * desc: 将mediator发起请求后转化为flow
 * @param viewModel 发起请求需要的viewModel
 * @param pagingConfig 分页设置，默认使用@link DEFAULT_PAGER 即可
 * @param clearData 请求前是否清空数据库中保存的数据
 * @param fetchData 查询数据库请求分页消息
 * @param transform 将数据库的模型转为ui上的模型的方法
 */
suspend inline fun <reified R : Any, reified T : Any> BaseRemoteMediator<R, T>.request(
    viewModel: ViewModel,
    pagingConfig: PagingConfig = DEFAULT_PAGER,
    clearData: Boolean = true,
    crossinline fetchData: () -> PagingSource<Int, R>,
    crossinline transform: (data: R) -> T
): Flow<PagingData<T>> {
    return viewModel.fetch(
        pagingConfig = pagingConfig,
        remoteMediator = this,
        clearData = clearData,
        fetchData = fetchData,
        transform = transform
    )
}