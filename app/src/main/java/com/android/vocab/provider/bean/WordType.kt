package com.android.vocab.provider.bean

import android.os.Parcel
import android.os.Parcelable

@Suppress("unused", "UNUSED_PARAMETER")
class WordType(var typeId: Long = 0L,
               var typeName: String = "",
               var abbr: String = ""): Parcelable {

    constructor(parcel: Parcel) : this(
            parcel.readLong(),
            parcel.readString(),
            parcel.readString())

    override fun writeToParcel(parcel: Parcel, flags: Int) {

    }

    override fun describeContents(): Int {
        return 0
    }

    override fun toString(): String {
        return "WordType(typeId=$typeId, typeName='$typeName', typeAbbr='$abbr')"
    }

    companion object CREATOR : Parcelable.Creator<WordType> {
        override fun createFromParcel(parcel: Parcel): WordType {
            return WordType(parcel)
        }

        override fun newArray(size: Int): Array<WordType?> {
            return arrayOfNulls(size)
        }
    }

}