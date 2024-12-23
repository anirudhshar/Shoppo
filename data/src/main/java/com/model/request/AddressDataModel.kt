package com.model.request

import com.model.AddressDomainModel
import kotlinx.serialization.Serializable

@Serializable
data class AddressDataModel(
    val addressLine: String,
    val city: String,
    val state: String,
    val postalCode: String,
    val country: String
){
    companion object{
        fun fromAddressDomainModel(addressDomainModel: AddressDomainModel): AddressDataModel{
            return AddressDataModel(
                addressLine = addressDomainModel.addressLine,
                city = addressDomainModel.city,
                state = addressDomainModel.state,
                postalCode = addressDomainModel.postalCode,
                country = addressDomainModel.country
            )
        }

    }
}
