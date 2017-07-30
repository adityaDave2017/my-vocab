package com.android.vocab.provider.bean

import android.os.Parcel
import android.os.Parcelable

open class Word(var wordId: Long = 0,
                var word: String = "",
                var typeId: Long = 0L,
                var meaning: String = "",
                var frequency: Int = 0,
                var createTime: Long = 0L,
                var lastAccessTime: Long = 0L
) : Parcelable {

    constructor(parcel: Parcel) : this(
            parcel.readLong(),
            parcel.readString(),
            parcel.readLong(),
            parcel.readString(),
            parcel.readInt(),
            parcel.readLong(),
            parcel.readLong()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeLong(wordId)
        parcel.writeString(word)
        parcel.writeLong(typeId)
        parcel.writeString(meaning)
        parcel.writeInt(frequency)
        parcel.writeLong(createTime)
        parcel.writeLong(lastAccessTime)
    }

    override fun describeContents(): Int {
        return 0
    }

    override fun toString(): String {
        return "Word(wordId=$wordId, word='$word', typeId=$typeId, meaning='$meaning', frequency=$frequency, createTime=$createTime, lastAccessTime=$lastAccessTime)"
    }

    companion object CREATOR : Parcelable.Creator<Word> {
        override fun createFromParcel(parcel: Parcel): Word {
            return Word(parcel)
        }

        override fun newArray(size: Int): Array<Word?> {
            return arrayOfNulls(size)
        }
    }


}