package com.ramilkapev.curiosityimagestesttask

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.google.android.flexbox.*
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

        cursor!!.moveToFirst()
            while (cursor.moveToNext()) {
                val values = cursor.getString(cursor.getColumnIndex(DBHelper.COLUMN_IMAGES))
                imageUrlList.add(values)
            }
        Log.d("asdmaincurs", imageUrlList.size.toString())
        cursor.close()
        dbHelper.close()

        val recyclerView: RecyclerView = findViewById(R.id.rv_images)

        val flexLayoutManager = FlexboxLayoutManager(this).apply {
            flexWrap = FlexWrap.WRAP
            flexDirection = FlexDirection.ROW
            alignItems = AlignItems.STRETCH
        }

        val flexAdapter = ImagesAdapter(this, imageUrlList)
        recyclerView.apply {
            setHasFixedSize(true)
            layoutManager = flexLayoutManager
            adapter = flexAdapter
        }
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