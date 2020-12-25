package com.ramilkapev.curiosityimagestesttask

import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.android.flexbox.*
import com.ramilkapev.curiosityimagestesttask.Http.HttpHandler

class MainActivity : AppCompatActivity() {

    private val imageUrlList = arrayListOf<String>()
    private val dbHelper = DBHelper(this, null)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val swipeContainer: SwipeRefreshLayout = findViewById(R.id.swipe_container)
        val recyclerView: RecyclerView = findViewById(R.id.rv_images)

        val httpHandler = HttpHandler()

        httpHandler.apiRequest(dbHelper)

        val cursor = dbHelper.getAllImages()
        getImagesFromDB(cursor!!)

        swipeContainer.setOnRefreshListener {
            httpHandler.apiRequest(dbHelper)
            getImagesFromDB(cursor!!)
            swipeContainer.isRefreshing = false
        }

        val flexLayoutManager = FlexboxLayoutManager(this).apply {
            flexWrap = FlexWrap.WRAP
            flexDirection = FlexDirection.ROW
            alignItems = AlignItems.STRETCH
        }

        val flexAdapter = ImagesAdapter(this, imageUrlList)
        recyclerView.apply {
            layoutManager = flexLayoutManager
            adapter = flexAdapter
            setHasFixedSize(true)
        }
    }

    fun getImagesFromDB(cursor: Cursor) {
        if (cursor!!.moveToFirst() && imageUrlList.isEmpty()) {
            while (cursor.moveToNext()) {
                val values = cursor.getString(cursor.getColumnIndex(DBHelper.COLUMN_IMAGES))
                imageUrlList.add(values)
            }
        }
        dbHelper.close()
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