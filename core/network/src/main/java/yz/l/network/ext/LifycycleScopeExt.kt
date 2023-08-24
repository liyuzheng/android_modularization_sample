package yz.l.network.ext

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import yz.l.network.BaseResponse
import yz.l.network.Repo
import yz.l.network.Result


/**
 * desc:
 * createed by liyuzheng on 2023/6/1 18:22
 */
inline fun <reified T : BaseResponse> CoroutineScope.request(
    repo: Repo, crossinline callback: (r: Result<T>) -> Unit
) {
    repo.toFlow<T>().completedIn(this, callback)
}

inline fun <reified T1 : BaseResponse, reified T2 : BaseResponse> CoroutineScope.request(
    repo1: Repo,
    repo2: Repo,
    crossinline callback: (r: Result<Pair<T1, T2>>) -> Unit
) {
    combine(repo1.toFlow<T1>(), repo2.toFlow<T2>()) { r1, r2 ->
        Pair(r1, r2)
    }.completedIn(this, callback)
}

inline fun <reified T1 : BaseResponse, reified T2 : BaseResponse, reified T3 : BaseResponse> CoroutineScope.request(
    repo1: Repo,
    repo2: Repo,
    repo3: Repo,
    crossinline callback: (r: Result<Triple<T1, T2, T3>>) -> Unit,
) {
    combine(
        repo1.toFlow<T1>(), repo2.toFlow<T2>(), repo3.toFlow<T3>()
    ) { r1, r2, r3 ->
        Triple(r1, r2, r3)
    }.completedIn(this, callback)
}

inline fun CoroutineScope.request(
    crossinline callback: (r: Result<Array<*>>) -> Unit,
    vararg repo: Repo
) {
    val requests = mutableListOf<Flow<BaseResponse>>()
    repo.forEach {
        requests.add(it.toFlow())
    }
    combine(*requests.toTypedArray()) { it }.completedIn(this, callback)
}