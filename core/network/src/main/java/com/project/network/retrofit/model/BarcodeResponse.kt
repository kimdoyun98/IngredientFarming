package com.project.network.retrofit.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class BarcodeResponse(
    @SerialName("I2570") val i2570: I2570 = I2570(),
)

@Serializable
data class I2570(
    @SerialName("total_count") val total_count: String = "0",
    @SerialName("row") val row: List<ProductResponse> = emptyList(),
)

@Serializable
data class ProductResponse(
    @SerialName("BRCD_NO") val brcd_no: String,
    @SerialName("PRDT_NM") val prdt_nm: String,
)
