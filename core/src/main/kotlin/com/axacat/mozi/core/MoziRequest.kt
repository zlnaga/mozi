package com.axacat.mozi.core

public class MoziRequest<T>(
    public val target: String,
    public val method: MoziMethod,
    public val headers: Map<String, List<String>> = emptyMap(),
    public val param: T
)