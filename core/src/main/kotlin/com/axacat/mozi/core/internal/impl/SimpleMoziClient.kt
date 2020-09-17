package com.axacat.mozi.core.internal.impl

import com.axacat.mozi.core.*
import com.axacat.mozi.core.internal.utils.extension.asMap
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.net.URI

internal class SimpleMoziClient : MoziClient {
    override fun <T : Any, R : Any> request(
        request: MoziRequest<T>,
        responseClazz: Class<R>,
        threadOn: Mozi.ThreadOn,
        responser: (MoziResponse<R>) -> Unit
    ) {
        GlobalScope.launch {
            val result = internalRequest(request)
            val gson = Gson()

            val response = object : MoziResponse<R> {
                override fun body(): R {
                    return gson.fromJson(result, responseClazz)
                }

                override fun headers(): Map<String, List<String>> {
                    return emptyMap()
                }

                override fun mediaType(): MediaType {
                    return MediaType.Application.Json
                }

            }

            withContext(
                when (threadOn) {
                    Mozi.ThreadOn.MAIN -> Dispatchers.Main
                    Mozi.ThreadOn.IO -> Dispatchers.IO
                    Mozi.ThreadOn.UNSPECIFIC -> Dispatchers.Default
                }
            ) {
                responser(response)
            }
        }
    }

    private fun <T : Any> internalRequest(
        request: MoziRequest<T>
    ): String {
        if (request.method == MoziMethod.GET) {
            var url = URI.create(request.target)
            if (request.param !is Nothing) {
                val map: Map<String, Any?> = request.param.asMap()
                val appendQuery = map.map {
                    String.format(
                        "%s=%s",
                        it.key,
                        it.value?.toString() ?: ""
                    )
                }.joinToString("&")

                var query = url.query
                if (query == null) {
                    query = appendQuery
                } else {
                    query += "&$appendQuery"
                }

                url = URI(url.scheme, url.authority, url.path, query, url.fragment)
            }

            val result = url.toURL().readText()

            try {
                return result
            } catch (error: Throwable) {
                error.printStackTrace()
            }
        } else if (request.method == MoziMethod.POST) {
            //TODO POST
        }

        return ""
    }
}