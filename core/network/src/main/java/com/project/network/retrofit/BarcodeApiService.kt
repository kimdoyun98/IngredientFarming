package com.project.network.retrofit

import com.project.network.BuildConfig
import com.project.network.retrofit.model.BarcodeResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface BarcodeApiService {
    @GET("api/{key}/I2570/json/0/5/BRCD_NO={barcode}")
    suspend fun getProductInfo(
        @Path("key") key: String = BuildConfig.BARCODE_API_KEY,
        @Path("barcode") barcode: String
    ): Response<BarcodeResponse>
}
