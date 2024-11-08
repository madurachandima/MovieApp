package com.madura.movieapp.data.converter

import androidx.room.TypeConverter

class RoomConverter {

    @TypeConverter
    fun convertBooleanToInt(boolean: Boolean):Int{
        return  if (boolean) 1 else 0
    }


    @TypeConverter
    fun convertIntToBoolean(int: Int):Boolean{
        return int ==1;
    }
}