package com.axacat.mozi.core

import com.axacat.mozi.core.internal.impl.SimpleMoziClient
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.concurrent.atomic.AtomicLong
import kotlin.concurrent.thread

public class Mozi {
    public enum class ThreadOn {
        MAIN,
        IO,
        UNSPECIFIC, ;
    }

    public fun deferred(text: (String) -> Unit) {
        GlobalScope.launch {
            defer {
                text(it)
            }
        }
    }

    private fun defer(callback: (String) -> Unit) {
        val c = AtomicLong()

        for (i in 1..10000L) {
            thread(start = true) {
                callback(c.addAndGet(i).toString())
            }
        }

        callback(c.toString())
    }

    public fun createSimpleClient(): MoziClient {
        return SimpleMoziClient()
    }
}