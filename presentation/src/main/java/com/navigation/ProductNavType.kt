package com.navigation

import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.navigation.NavType
import com.model.UiProductModel
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.net.URLDecoder
import java.net.URLEncoder
import java.util.Base64

val productNavType = object : NavType<UiProductModel>(isNullableAllowed = false) {


    override fun get(bundle: Bundle, key: String): UiProductModel? {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU){

        return bundle.getParcelable(key, UiProductModel::class.java)
        }
        else{
            return bundle.getParcelable(key) as UiProductModel?
        }
    }


    @RequiresApi(Build.VERSION_CODES.O)
    override fun parseValue(value: String): UiProductModel {
        val item = Json.decodeFromString<UiProductModel>(value)
        return item.copy(
            image = URLDecoder.decode(item.image, "utf-8"),
            description = String(Base64.getDecoder().decode(item.description.replace("_", "/"))),
            title = String(Base64.getDecoder().decode(item.title.replace("_", "/")))

        )

    }


    @RequiresApi(Build.VERSION_CODES.O)
    override fun serializeAsValue(value: UiProductModel): String {
        return Json.encodeToString(
            value.copy(
                image = URLEncoder.encode(value.image, "utf-8"),
                description = String(
                    Base64.getEncoder().encode(value.description.toByteArray())
                ).replace("/", "_"),
                title = String(Base64.getEncoder().encode(value.title.toByteArray())).replace(
                    "/",
                    "_"
                ),

                )
        )

    }


    override fun put(bundle: Bundle, key: String, value: UiProductModel) {
        bundle.putParcelable(key, value)
    }


}