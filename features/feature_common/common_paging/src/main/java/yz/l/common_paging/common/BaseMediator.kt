package yz.l.common_paging.common

import androidx.paging.*
import yz.l.data.common.RemoteDB
import yz.l.network.ResponseEmpty
import yz.l.network.ext.toResponseException

/**
 * desc:
 * @param db 数据库
 * @param remoteName 每个remoteName必须唯一，控制页面的加载逻辑，数据库存储逻辑
 * createed by liyuzheng on 2020/1/15 11:38
 */
@OptIn(ExperimentalPagingApi::class)
abstract class BaseRemoteMediator<V : Any, T : Any>(
    open val remoteName: String
) : RemoteMediator<Int, V>() {
    /**
     * 获取loadKey,也就是页码数
     * 本方法只有在LoadType.APPEND时回调
     * LoadType.PREPEND 本项目暂时不涉及到，所以直接回调
     * LoadType.REFRESH 回调{@defaultRefreshLoadKey()}方法
     */
    open suspend fun initLoadKey(
        remoteName: String,
    ): String? {
        return RemoteDB.getRemoteKeysAsync(remoteName)?.next
    }

    /**
     * 刷新时页码数，通常情况为""
     */
    open fun defaultRefreshLoadKey(remoteName: String) = ""

    /**
     * 网络获取数据,并存入数据库中。
     * @return endOfPaginationReached true 列表没有更多数据了，false 列表还有更多数据
     */
    abstract suspend fun load(
        loadKey: String,
        loadType: LoadType,
        pageConfig: PagingConfig
    ): Boolean

    /**
     * 刷新时空页面的的回调Exception
     * @return ResponseEmptyException 配合EmptyViewholder使用，不同情况使用不同样式
     */
    open fun customEmpty(remoteName: String): ResponseEmpty {
        return ResponseEmpty()
    }

    open suspend fun clearLocalData() {}

    final override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, V>
    ): MediatorResult {
        val loadKey: String = when (loadType) {
            LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
            LoadType.APPEND -> initLoadKey(remoteName) ?: defaultRefreshLoadKey(remoteName)
            else -> defaultRefreshLoadKey(remoteName)
        }
        return try {
            val endOfPaginationReached = load(loadKey, loadType, state.config)
            if (endOfPaginationReached && loadType == LoadType.REFRESH) {
                MediatorResult.Error(customEmpty(remoteName))
            } else {
                MediatorResult.Success(endOfPaginationReached)
            }
        } catch (ex: Exception) {
            val e = ex.toResponseException()
            MediatorResult.Error(e)
        }
    }
}