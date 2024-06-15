package com.app.tripnary.domain.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class TaskModel(
    var reference: String,
    var id: String,
    var text: String,
    var done: Boolean
) : Parcelable
