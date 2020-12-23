package com.ramilkapev.curiosityimagestesttask

import kotlinx.serialization.Serializable

@Serializable
data class ImagesModel(
    val id: Int,
    val author: String,
    val width: Int,
    val height: Int,
    val url: String,
    val download_url: String)