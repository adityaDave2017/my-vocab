package com.android.vocab.provider.bean

import android.os.Parcel
import android.os.Parcelable


@Suppress("unused")
class SynonymWord(wordId: Long,
                  word: String,
                  typeId: Long,
                  meaning: String,
                  frequency: Int,
                  createTime: Long,
                  lastAccessTime: Long,
                  val synonymId: Long
    ): Word(wordId, word, typeId, meaning, frequency, createTime, lastAccessTime)  {


    constructor(parcel: Parcel) : this(
            parcel.readLong(),
            parcel.readString(),
            parcel.readLong(),
            parcel.readString(),
            parcel.readInt(),
            parcel.readLong(),
            parcel.readLong(),
            parcel.readLong()
    )


    override fun writeToParcel(parcel: Parcel, flags: Int) {
        super.writeToParcel(parcel, flags)
        parcel.writeLong(synonymId)
    }


    override fun describeContents(): Int {
        return 0
    }


    companion object CREATOR : Parcelable.Creator<SynonymWord> {
        override fun createFromParcel(parcel: Parcel): SynonymWord {
            return SynonymWord(parcel)
        }

        override fun newArray(size: Int): Array<SynonymWord?> {
            return arrayOfNulls(size)
        }
    }

}