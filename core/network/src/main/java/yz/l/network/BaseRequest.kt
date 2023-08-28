package yz.l.network

import okhttp3.MediaType
import java.io.File

class BaseRequest {
    var api: String? = null

    var requestMode: Int = RequestMode.GET

    var file: File? = null

    private var params = mutableMapOf<String, Any?>()

    var contentType: MediaType? = null

    fun api(init: () -> String) {
        api = init()
    }

    fun requestMode(init: () -> Int) {
        requestMode = init()
    }

    fun file(init: () -> File) {
        file = init()
    }

    fun params(init: () -> Pair<String, Any?>) {
        val p = init()
        params[p.first] = p.second
    }

    fun contentType(init: () -> MediaType?) {
        contentType = init()
    }

    override fun toString(): String {
        return "api:$api \n params :$params"
    }

    fun reflectParameters(): MutableMap<String, Any?> {
        return params
    }

}