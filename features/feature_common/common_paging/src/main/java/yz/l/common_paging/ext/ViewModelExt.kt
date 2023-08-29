package yz.l.common_paging.ext

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import yz.l.common_paging.common.BaseRemoteMediator
import yz.l.common_paging.common.DEFAULT_PAGER

/**
 * desc:
 * createed by liyuzheng on 2023/3/28 15:07
 */
/**
 * paging使用
 */
@OptIn(ExperimentalPagingApi::class)
/**
 * desc: ViewModel发起分页请求
 * @param pagingConfig 分页设置，默认使用@link DEFAULT_PAGER 即可
 * @param remoteMediator paging请求使用的mediator
 * @param clearData 请求前是否清空数据库中保存的数据
 * @param fetchData 查询数据库请求分页消息
 * @param transform 将数据库的模型转为ui上的模型的方法
 */
suspend inline fun <reified T : Any, reified R : Any> ViewModel.fetch(
    pagingConfig: PagingConfig = DEFAULT_PAGER,
    remoteMediator: BaseRemoteMediator<R, T>,
    clearData: Boolean = true,
    crossinline fetchData: () -> PagingSource<Int, R>,
    crossinline transform: (data: R) -> T
): Flow<PagingData<T>> {
    if (clearData) {
        remoteMediator.clearLocalData()
    }
    return Pager(
        config = pagingConfig,
        remoteMediator = remoteMediator
    ) {
        fetchData()
    }.flow.map { data: PagingData<R> ->
        data.map { r: R ->
            transform(r)
        }
    }.cachedIn(viewModelScope)
}