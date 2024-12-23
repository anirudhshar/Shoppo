package com.navigation

import android.os.Parcelable
import com.model.UserAddress
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Serializable
@Parcelize
data class UserAddressWrapper(
    val userAddress: UserAddress?
):Parcelable