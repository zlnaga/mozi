package com.axacat.mozi.core.internal.utils.extension

internal fun Any.asMap(): Map<String, Any?> {
    val fields = this::class.java.declaredFields
    return fields.associate {
        it.isAccessible = true
        it.name to it.get(this)
    }
}