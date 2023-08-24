package yz.l.core_router

/**
 * desc:
 * createed by liyuzheng on 2023/8/24 17:15
 */
object Router {
    private val routerServices = mutableMapOf<String, RouterService>()
    fun register(path: String, provider: RouterService) {
        if (routerServices.containsKey(path)) {
            throw DuplicatePathException("path重复注册。")
        }
        routerServices[path] = provider
    }

    fun getProvider(path: String): RouterService {
        return routerServices[path] ?: throw NullPointerException("没有找到对应的provider")
    }
}