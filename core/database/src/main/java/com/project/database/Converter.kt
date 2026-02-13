package com.project.database

import androidx.room.TypeConverter
import com.project.model.barcode.IngredientCategory
import java.time.LocalDate
import java.time.format.DateTimeFormatter

internal class Converter {
    @TypeConverter
    fun localDateTimeToString(date: LocalDate?): String? {
        return date?.format(DateTimeFormatter.ISO_LOCAL_DATE)
    }

    @TypeConverter
    fun stringToLocalDateTime(dateString: String?): LocalDate? {
        return dateString?.toLocalDate()
    }

//    @TypeConverter
//    fun enumToString(value: IngredientCategory) = value.toString()
//    @TypeConverter
//    fun stringToMyEnum(value: String) = value.toEnum<IngredientCategory>()

    private fun String.toLocalDate(): LocalDate = LocalDate
        .parse(this, DateTimeFormatter.ISO_LOCAL_DATE)

    @Suppress("NOTHING_TO_INLINE")
    private inline fun <T : Enum<T>> T.toString(): String = this.name
    private inline fun <reified T : Enum<T>> String.toEnum(): T = enumValueOf(this)
}
