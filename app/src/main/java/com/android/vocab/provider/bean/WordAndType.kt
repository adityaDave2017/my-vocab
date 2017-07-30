package com.android.vocab.provider.bean

import android.os.Parcel
import android.os.Parcelable


class WordAndType(var nwordId: Long = 0,
                  var nword: String = "",
                  var ntypeId: Long = 0L,
                  var nmeaning: String = "",
                  var nfrequency: Int = 0,
                  var ncreateTime: Long = 0L,
                  var nlastAccessTime: Long = 0L,
                  var typeName: String = "",
                  var abbr: String = "") : Word(nwordId, nword, ntypeId, nmeaning, nfrequency, ncreateTime, nlastAccessTime) {

    constructor(parcel: Parcel) : this(
            parcel.readLong(),
            parcel.readString(),
            parcel.readLong(),
            parcel.readString(),
            parcel.readInt(),
            parcel.readLong(),
            parcel.readLong(),
            parcel.readString(),
            parcel.readString()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeLong(nwordId)
        parcel.writeString(nword)
        parcel.writeLong(ntypeId)
        parcel.writeString(nmeaning)
        parcel.writeInt(nfrequency)
        parcel.writeLong(ncreateTime)
        parcel.writeLong(nlastAccessTime)
        parcel.writeString(typeName)
        parcel.writeString(abbr)
    }

    override fun describeContents(): Int {
        return 0
    }

    override fun toString(): String {
        return "WordAndType(nwordId=$nwordId, nword='$nword', ntypeId=$ntypeId, nmeaning='$nmeaning', nfrequency=$nfrequency, ncreateTime=$ncreateTime, nlastAccessTime=$nlastAccessTime, typeName='$typeName', abbr='$abbr')"
    }

    companion object CREATOR : Parcelable.Creator<Word> {
        override fun createFromParcel(parcel: Parcel): Word {
            return WordAndType(parcel)
        }

        override fun newArray(size: Int): Array<Word?> {
            return arrayOfNulls(size)
        }
    }

}