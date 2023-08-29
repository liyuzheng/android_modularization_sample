package yz.l.common_paging.common

import androidx.paging.PagingConfig

const val PAGE_SIZE = 20

val DEFAULT_PAGER = PagingConfig(
    // 每页显示的数据的大小
    pageSize = PAGE_SIZE,
    initialLoadSize = PAGE_SIZE * 2,
    prefetchDistance = 5,
    enablePlaceholders = true
)