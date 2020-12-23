package com.ramilkapev.curiosityimagestesttask

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.DatabaseUtils
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log

class DBHelper(context: Context, factory: SQLiteDatabase.CursorFactory?) :
    SQLiteOpenHelper(context, DATABASE_NAME, factory, DATABASE_VERSION) {
    override fun onCreate(db: SQLiteDatabase) {
        val createImagesTable = ("CREATE TABLE " +
                TABLE_IMAGES + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT UNIQUE,"
                + COLUMN_UNIQUE_IMAGE_ID + " INTEGER UNIQUE,"
                + COLUMN_IMAGES
                + " TEXT" + ")")
        db.execSQL(createImagesTable)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_IMAGES")
        if (db != null) {
            onCreate(db)
        }
    }

    fun addImage(model: ImagesModel) {
        val values = ContentValues()
        values.put(COLUMN_IMAGES, model.download_url)
        values.put(COLUMN_UNIQUE_IMAGE_ID, model.id)
//        values.put(COLUMN_ID, pos)
        val db = this.writableDatabase
        db?.replace(TABLE_IMAGES, null, values)
    }

    fun deleteImage(imageId: Int){
        val db = writableDatabase
        val res = db.delete(TABLE_IMAGES, "$COLUMN_ID=?", arrayOf(imageId.toString()))
        Log.d("asddelres", res.toString())
    }

    fun getAllImages(): Cursor? {
        val db = this.readableDatabase
        return db.rawQuery("SELECT * FROM $TABLE_IMAGES", null)
    }

    companion object {
        private val DATABASE_VERSION = 1
        private val DATABASE_NAME = "images.db"
        val TABLE_IMAGES = "images"
        val COLUMN_ID = "_id"
        val COLUMN_UNIQUE_IMAGE_ID = "image_unique_id"
        val COLUMN_IMAGES = "image"
    }
}