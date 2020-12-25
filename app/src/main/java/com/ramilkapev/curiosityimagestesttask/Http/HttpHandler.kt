package com.ramilkapev.curiosityimagestesttask.Http

import android.util.Log
import com.ramilkapev.curiosityimagestesttask.*
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import okhttp3.*
import java.io.IOException
import java.lang.Exception

class HttpHandler {
    private val client = OkHttpClient()
    private var imagesList: List<ImagesModel> = arrayListOf()

    fun apiRequest(dbHelper: DBHelper) {

        val request = Request.Builder()
            .url(url)
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                e.printStackTrace()
            }

            override fun onResponse(call: Call, response: Response) {
                response.use {
                    if (!response.isSuccessful) throw IOException("Unexpected code $response")

                    val jsonData = response.body!!.string()

                    imagesList = Json.decodeFromString(jsonData)

                    for (i in imagesList) {
                        dbHelper.addImage(i)
                    }
                }
            }
        })
    }

    companion object {
        const val url = "https://picsum.photos/v2/list?page=1&limit=20"
    }
}