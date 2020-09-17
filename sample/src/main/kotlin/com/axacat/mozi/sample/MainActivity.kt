package com.axacat.mozi.sample

import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.annotation.Keep
import androidx.appcompat.app.AppCompatActivity
import com.axacat.mozi.core.Mozi
import com.axacat.mozi.core.MoziMethod
import com.axacat.mozi.core.MoziRequest

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val text = findViewById<TextView>(R.id.text)

        val request = MoziRequest(
            target = "http://0xbb.cn:1101/bang/video",
            method = MoziMethod.GET,
            param = UrlSource(
                url = "https://weibo.com/5500491427/JkT6340rH"
            )
        )
        val mozi = Mozi()
//        mozi.deferred { deferred ->
//            Thread.sleep(1000)
//            runOnUiThread {
//                text.text = deferred
//            }
//        }

        val composer = mozi.createSimpleClient()
        composer.request(request, VideoSource::class.java) {
            Log.d("TTT", it.body().toString())
            text.text = it.body().data.video.urls.last().url
        }

    }
}

@Keep
data class UrlSource(
    val url: String
)

@Keep
data class VideoSource(
    val `data`: Data,
    val message: String,
    val success: Boolean
)

@Keep
data class Data(
    val type: String,
    val video: Video
)

@Keep
data class Video(
    val poster: Poster,
    val urls: List<Url>
)

@Keep
data class Poster(
    val height: Int,
    val url: String,
    val width: Int
)

@Keep
data class Url(
    val duration: Double,
    val format: String,
    val height: Int,
    val label: String,
    val url: String,
    val width: Int
)