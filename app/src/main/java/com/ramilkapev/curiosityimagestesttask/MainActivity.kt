package com.ramilkapev.curiosityimagestesttask

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ramilkapev.curiosityimagestesttask.Http.HttpHandler

class MainActivity : AppCompatActivity() {

    private val imageUrlList = arrayListOf<String>()
    private val dbHelper = DBHelper(this, null)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val httpHandler = HttpHandler()

        httpHandler.apiRequest(dbHelper)
        val cursor = dbHelper.getAllImages()

//        imageUrlList.clear()
        Log.d("asd33", cursor!!.moveToFirst().toString())
        Log.d("asd33listempty", imageUrlList.isEmpty().toString())

        cursor.moveToFirst()
        if (cursor!!.moveToFirst()) {
            while (cursor.moveToNext()) {
                val values = cursor.getString(cursor.getColumnIndex(DBHelper.COLUMN_IMAGES))
                imageUrlList.add(values)
            }
        }
        cursor.close()
        dbHelper.close()

        val recyclerView: RecyclerView = findViewById(R.id.rv_images)

        val gridLayoutManager = GridLayoutManager(this, 2)
        recyclerView.layoutManager = gridLayoutManager

        val adapter = ImagesAdapter(this, imageUrlList)
        recyclerView.adapter = adapter
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putStringArrayList("imagesList", imageUrlList)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        savedInstanceState.getIntegerArrayList("imagesList")
    }

}