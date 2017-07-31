package com.android.vocab.provider.bean

import android.os.Parcel
import android.os.Parcelable


class WordAndType(nwordId: Long = 0,
                  nword: String = "",
                  ntypeId: Long = 0L,
                  nmeaning: String = "",
                  nfrequency: Int = 0,
                  ncreateTime: Long = 0L,
                  nlastAccessTime: Long = 0L,
                  val typeName: String = "",
                  val typeAbbr: String = "") : Word(nwordId, nword, ntypeId, nmeaning, nfrequency, ncreateTime, nlastAccessTime), Cloneable {

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
        super.writeToParcel(parcel, flags)
        parcel.writeString(typeName)
        parcel.writeString(typeAbbr)
    }

    override fun describeContents(): Int {
        return 0
    }

    override fun toString(): String {
        return "WordAndType(wordId=$wordId, word='$word', typeId=$typeId, meaning='$meaning', frequency=$frequency, createTime=$createTime, lastAccessTime=$lastAccessTime, typeName='$typeName', typeAbbr='$typeAbbr')"
    }

    override fun equals(other: Any?): Boolean {
        if (other is WordAndType) {
            return this.word == other.word && this.meaning == other.meaning && this.typeId == other.typeId
        }
        return false
    }

    override fun hashCode(): Int {
        var result = wordId.hashCode()
        result = 31 * result + word.hashCode()
        result = 31 * result + typeId.hashCode()
        result = 31 * result + meaning.hashCode()
        result = 31 * result + frequency
        result = 31 * result + createTime.hashCode()
        result = 31 * result + lastAccessTime.hashCode()
        result = 31 * result + typeName.hashCode()
        result = 31 * result + typeAbbr.hashCode()
        return result
    }

    public override fun clone(): WordAndType {
        return WordAndType(
                this.wordId,
                this.word,
                this.typeId,
                this.meaning,
                this.frequency,
                this.createTime,
                this.lastAccessTime,
                this.typeName,
                this.typeAbbr
        )
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