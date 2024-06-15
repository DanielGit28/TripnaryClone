package com.app.tripnary.data.converter.array
import androidx.room.TypeConverter

class ArrayStringConverter {
    @TypeConverter
    fun fromArrayString(array: Array<String>): String {
        return array.joinToString(",")
    }

    @TypeConverter
    fun toArrayString(data: String): Array<String> {
        return data.split(",").toTypedArray()
    }
}