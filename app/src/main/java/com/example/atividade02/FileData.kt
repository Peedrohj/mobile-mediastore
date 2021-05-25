package com.example.atividade02

import android.net.Uri
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class FileData(
        val name: String,
        val imageUri:  Uri?
): Parcelable