package com.android.vocab.provider.bean

import android.os.Parcel
import android.os.Parcelable


@Suppress("unused", "UNUSED_PARAMETER")
class Antonym(var antonymId: Long = 0L,
              var mainWordId: Long = 0L,
              var antonymWordId: Long = 0L): Parcelable {

    constructor(parcel: Parcel) : this(
            parcel.readLong(),
            parcel.readLong(),
            parcel.readLong())

    override fun writeToParcel(parcel: Parcel, flags: Int) {

    }

    override fun describeContents(): Int {
        return 0
    }

    override fun toString(): String {
        return "Antonym(antonymId=$antonymId, mainWordId=$mainWordId, antonymWordId=$antonymWordId)"
    }

    companion object CREATOR : Parcelable.Creator<Antonym> {
        override fun createFromParcel(parcel: Parcel): Antonym {
            return Antonym(parcel)
        }

        override fun newArray(size: Int): Array<Antonym?> {
            return arrayOfNulls(size)
        }
    }

}