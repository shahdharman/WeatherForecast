package com.example.weatherforecast.model

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
/*
* When working with Room, each table is represented by a class. In an
* ORM (Object Relational Mapping) library, such as Room, these are often called
* model classes, or entities.
*
*
* Typically, SQL column names will have words separated by an underscore,
* as opposed to the lowerCamelCase used by Kotlin properties
* */
@Entity(tableName = "fav_tbl")
data class Favourite(
    @NonNull
    @PrimaryKey
    @ColumnInfo(name = "city")
    val city:String,
    @ColumnInfo(name = "country")
    val country:String
){

}