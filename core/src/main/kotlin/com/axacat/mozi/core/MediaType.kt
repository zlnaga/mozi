package com.axacat.mozi.core

public sealed class MediaType(
    public val type: String
) {
    public object Undefined : MediaType("undefined")

    public sealed class Application(
        subType: String
    ) : MediaType("application/$subType") {
        public object Undefined : Application("undefined")

        public object Json : Application("json")
    }

    public fun wrap(typeString: String): MediaType {
        return if (typeString.contains("/")) {
            val types = typeString.split("/")
            when (types[0]) {
                "application" -> {
                    if (types.size > 1) {
                        when (types[1]) {
                            "json" -> Application.Json
                            else -> Application.Undefined
                        }
                    } else {
                        Application.Undefined
                    }
                }
                else -> Undefined
            }
        } else {
            Undefined
        }
    }
}