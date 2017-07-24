package com.android.vocab.bean

import android.os.Parcel
import android.os.Parcelable
import java.text.DateFormat
import java.util.*

class Word (
        var wordId: Int = 0,
        var word: String = "",
        var wordType: String = "",
        var meaning: String = "",
        var frequency: Int = 0,
        var createTime: Date = Date(),
        var lastAccessTime: Date = Date()
): Parcelable {

    constructor(parcel: Parcel) : this(
            parcel.readInt(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readInt()
//            DateFormat.getInstance().parse(parcel.readString()),
//            DateFormat.getInstance().parse(parcel.readString())
 )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(wordId)
        parcel.writeString(word)
        parcel.writeString(wordType)
        parcel.writeString(meaning)
        parcel.writeInt(frequency)
//        parcel.writeString(createTime.toString())
//        parcel.writeString(lastAccessTime.toString())
    }

    override fun describeContents(): Int {
        return 0
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