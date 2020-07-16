package com.mokelab.tools.shortcut.model.preset

import android.os.Parcel
import android.os.Parcelable

class PresetShortcut(
    val label: String,
    val packageName: String,
    val componentName: String,
    val action: String
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!
    )

    override fun toString(): String {
        return this.label
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(label)
        parcel.writeString(packageName)
        parcel.writeString(componentName)
        parcel.writeString(action)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<PresetShortcut> {
        override fun createFromParcel(parcel: Parcel): PresetShortcut {
            return PresetShortcut(parcel)
        }

        override fun newArray(size: Int): Array<PresetShortcut?> {
            return arrayOfNulls(size)
        }
    }
}

