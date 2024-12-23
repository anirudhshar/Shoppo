package com.model


import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Serializable
@Parcelize
data class UserAddress(
    val addressLine: String,
    val city: String,
    val state: String,
    val postalCode: String,
    val country: String
): Parcelable {
    override fun toString(): String {
        return "$addressLine, $city, $state, $postalCode, $country"
    }

    fun toAddressDomainModel(): AddressDomainModel {
        return AddressDomainModel(
            addressLine = addressLine,
            city = city,
            state = state,
            postalCode = postalCode,
            country = country
        )
    }

}