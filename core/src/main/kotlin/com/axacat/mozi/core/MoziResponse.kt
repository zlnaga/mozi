package com.axacat.mozi.core

public interface MoziResponse<T> {
    public fun body(): T
    public fun headers(): Map<String, List<String>>
    public fun mediaType(): MediaType
}