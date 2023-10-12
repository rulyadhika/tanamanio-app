package com.rulyadhika.tanamanio.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class PlantCategory(
    val categoryName:String,
    val categoryImage:Int
) : Parcelable
