package com.ramilkapev.curiosityimagestesttask

import android.database.Cursor

interface DBView {
    fun addImage(model: ImagesModel)
    fun deleteImage(imageId: Int)
    fun getAllImages(): Cursor?
}