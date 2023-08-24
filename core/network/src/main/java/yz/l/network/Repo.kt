package yz.l.network

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody

class Repo {
    var req: BaseRequest = BaseRequest()

    private fun injectApiService(): Api {
        return RetrofitClient.provideRetrofit().create(Api::class.java)
    }

    suspend fun execute() = withContext(Dispatchers.IO) {
        val request = req
        val params = request.reflectParameters()
        val apiService = injectApiService()

        val api = request.api ?: throw RuntimeException("repo没有传入地址")
        when (request.requestMode) {
            RequestMode.MULTIPART -> {
                val requestBody: RequestBody =
                    request.file?.asRequestBody(request.contentType)
                        ?: throw RuntimeException("execute MULTIPART 时，file 不能为空")
                apiService.uploadFile(
                    api,
                    MultipartBody.Part.createFormData(
                        "uploadFile",
                        request.file?.name,
                        requestBody
                    ),
                )
            }

            RequestMode.POST -> {
                apiService.post(
                    api,
                    buildBody(params)
                )
            }

            RequestMode.GET -> {
                apiService.get(
                    api,
                    buildBody(params)
                )
            }

            RequestMode.PUT -> {
                apiService.put(
                    api,
                    buildBody(params)
                )
            }

            RequestMode.DELETE -> {
                apiService.delete(
                    api,
                    buildBody(params)
                )
            }

            RequestMode.DELETE_BODY -> {
                apiService.deleteBody(
                    api,
                    buildBody(params)
                )
            }

            else -> {
                throw UnsupportedOperationException("不支持的requestMode")
            }
        }
    }

    private fun buildBody(body: MutableMap<String, Any>): MutableMap<String, Any> {
        return body
    }

}