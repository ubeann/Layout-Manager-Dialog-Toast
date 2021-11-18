package com.dicoding.layout_manager_dialog_toast

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Subject(
    var image:Int,
    var name:String,
    var description:String
): Parcelable
