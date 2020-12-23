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
                + COLUMN_ID + " INTEGER PRIMARY KEY," +
                COLUMN_IMAGES
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
        val db = this.writableDatabase
        val sd = DatabaseUtils.longForQuery(db.compileStatement("SELECT COUNT(*) FROM $TABLE_IMAGES as count"), null)
        Log.d("asdcur", sd.toString())
        db?.insert(TABLE_IMAGES, null, values)
    }

    fun deleteImage(imageId: Int){
        val db = writableDatabase
        db.delete(TABLE_IMAGES,"$COLUMN_IMAGES=?", arrayOf(imageId.toString()))
    }

    private fun validateIfTableHasData(myDatabase: SQLiteDatabase, tableName: String): Boolean {
        val c = myDatabase.rawQuery("SELECT * FROM $tableName", null)
        return c.moveToFirst()
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
        val COLUMN_IMAGES = "image"
    }
}