package com.pemeluksenja.storyappdicoding.utils

import androidx.room.TypeConverter

class Converter {
    @TypeConverter
    fun anyToString(value: Any?): String? {
        return value?.toString()
    }
}