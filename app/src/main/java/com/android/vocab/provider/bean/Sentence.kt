package com.android.vocab.provider.bean

import android.os.Parcel
import android.os.Parcelable


@Suppress("unused", "UNUSED_PARAMETER")
class Sentence(var sentenceId: Long = 0L,
               var wordId: Long = 0L,
               var sentence: String = "",
               var createTime: Long = 0L): Parcelable {

    constructor(parcel: Parcel) : this(
            parcel.readLong(),
            parcel.readLong(),
            parcel.readString(),
            parcel.readLong())

    override fun writeToParcel(parcel: Parcel, flags: Int) {

    }

    override fun describeContents(): Int {
        return 0
    }

    override fun toString(): String {
        return "Sentence(sentenceId=$sentenceId, wordId=$wordId, sentence='$sentence', createTime=$createTime)"
    }

    companion object CREATOR : Parcelable.Creator<Sentence> {
        override fun createFromParcel(parcel: Parcel): Sentence {
            return Sentence(parcel)
        }

        override fun newArray(size: Int): Array<Sentence?> {
            return arrayOfNulls(size)
        }
    }

}