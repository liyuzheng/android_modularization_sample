package yz.l.network.ext

import com.google.gson.JsonSyntaxException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import yz.l.core_tool.ext.toObject
import yz.l.network.BaseRequest
import yz.l.network.BaseResponse
import yz.l.network.Repo
import yz.l.network.Result
import yz.l.network.StringBaseResponse

/**
 * desc:
 * createed by liyuzheng on 2023/3/3 15:03
 */

/***********************************build request*****************************************/
/**
 * dsl build repo
 */
inline fun repo(init: BaseRequest.() -> Unit): Repo {
    val repo = Repo()
    val req = BaseRequest()
    req.init()
    repo.req = req
    return repo
}

/***********************************build request*****************************************/


/**
 * repo 返回值build为flow
 */
inline fun <reified T : BaseResponse> Repo.toFlow() = flow {
    val jsonResult = execute()
    //接收String类型返回值，特殊处理
    if (T::class.java == StringBaseResponse::class.java) {
        emit(StringBaseResponse(jsonResult) as T)
    } else {
        val rsp: T = jsonResult.toObject<T>()
            ?: throw JsonSyntaxException("请求网络执行结果转化为object失败")
        emit(rsp)
    }
}.flowOn(Dispatchers.IO)

/**
 * request one repo
 */
inline fun <reified T : BaseResponse> Repo.request(
    coroutineScope: CoroutineScope, crossinline callback: (r: Result<T>) -> Unit
) {
    coroutineScope.request(this, callback)
}

suspend inline fun <reified T : BaseResponse> Repo.request(): T {
    val jsonResult = execute()
    return if (T::class.java == StringBaseResponse::class.java) {
        StringBaseResponse(jsonResult) as T
    } else {
        jsonResult.toObject<T>() ?: throw JsonSyntaxException("请求网络执行结果转化为object失败")
    }
}