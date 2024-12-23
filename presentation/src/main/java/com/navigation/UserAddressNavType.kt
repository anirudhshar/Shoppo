package com.navigation

import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.navigation.NavType
import com.model.UiProductModel
import com.model.UserAddress
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.net.URLDecoder
import java.net.URLEncoder
import java.util.Base64


val userAddressNavType = object : NavType<UserAddressWrapper>(isNullableAllowed = false) {


    override fun get(bundle: Bundle, key: String): UserAddressWrapper? {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU){

            return bundle.getParcelable(key, UserAddressWrapper::class.java)
        }
        else{
            return bundle.getParcelable(key) as UserAddressWrapper?
        }
    }


    @RequiresApi(Build.VERSION_CODES.O)
    override fun parseValue(value: String): UserAddressWrapper {
        val item = Json.decodeFromString<UserAddressWrapper>(value)
        return item

    }


    @RequiresApi(Build.VERSION_CODES.O)
    override fun serializeAsValue(value: UserAddressWrapper): String {
        return Json.encodeToString(
            value
        )

    }


    override fun put(bundle: Bundle, key: String, value: UserAddressWrapper) {
        bundle.putParcelable(key, value)
    }


}