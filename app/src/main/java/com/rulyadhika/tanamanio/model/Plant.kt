package com.rulyadhika.tanamanio

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Plant(
    val plantName:String,
    val plantLatinaName:String,
    val plantDescription:String,
    val plantCategory: String,
    val plantDifficulty: String,
    val plantPicture: String,
    val plantHowToCare: String,
    var isSelected: Boolean = false
) : Parcelable
