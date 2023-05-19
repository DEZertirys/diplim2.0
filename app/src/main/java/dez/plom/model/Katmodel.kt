package dez.plom.model

import android.os.Parcel
import android.os.Parcelable

class Katmodel(val name: String?, val address: String?, val delivery_charge: String?,
               val image: String?, var menus: List<Menus?>?) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.createTypedArrayList(Menus)
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
        parcel.writeString(address)
        parcel.writeString(delivery_charge)
        parcel.writeString(image)
        parcel.writeTypedList(menus)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Katmodel> {
        override fun createFromParcel(parcel: Parcel): Katmodel {
            return Katmodel(parcel)
        }

        override fun newArray(size: Int): Array<Katmodel?> {
            return arrayOfNulls(size)
        }
    }
}

data class Menus(val name: String?, val price: Float,  val url: String?, var totalInCart: Int) :
    Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readFloat(),
        parcel.readString(),
        parcel.readInt()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
        parcel.writeFloat(price)
        parcel.writeString(url)
        parcel.writeInt(totalInCart)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Menus> {
        override fun createFromParcel(parcel: Parcel): Menus {
            return Menus(parcel)
        }

        override fun newArray(size: Int): Array<Menus?> {
            return arrayOfNulls(size)
        }
    }
}