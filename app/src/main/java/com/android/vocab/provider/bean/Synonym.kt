package com.android.vocab.provider.bean

import android.os.Parcel
import android.os.Parcelable


@Suppress("unused", "UNUSED_PARAMETER")
class Synonym(var synonymId: Long = 0L,
              var mainWordId: Long = 0L,
              var synonymWordId: Long = 0L): Parcelable {

    constructor(parcel: Parcel) : this(
            parcel.readLong(),
            parcel.readLong(),
            parcel.readLong()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {

    }

    override fun describeContents(): Int {
        return 0
    }

    override fun toString(): String {
        return "Synonym(synonymId=$synonymId, mainWordId=$mainWordId, synonymWordId=$synonymWordId)"
    }

    companion object CREATOR : Parcelable.Creator<Synonym> {
        override fun createFromParcel(parcel: Parcel): Synonym {
            return Synonym(parcel)
        }

        override fun newArray(size: Int): Array<Synonym?> {
            return arrayOfNulls(size)
        }
    }

}