package com.project.network.retrofit.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class BarcodeResponse(
    @SerialName("I2570") val i2570: I2570Response?,
)

@Serializable
data class I2570Response(
    @SerialName("total_count") val total_count: String?,
    @SerialName("row") val row: List<ProductResponse>?,
)

@Serializable
data class ProductResponse(
    @SerialName("BRCD_NO") val brcd_no: String,
    @SerialName("PRDT_NM") val prdt_nm: String,
)
