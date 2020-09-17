package com.axacat.mozi.core

public interface MoziClient {
    public fun <T : Any, R : Any> request(
        request: MoziRequest<T>,
        responseClazz: Class<R>,
        threadOn: Mozi.ThreadOn = Mozi.ThreadOn.UNSPECIFIC,
        responser: (MoziResponse<R>) -> Unit
    )
}