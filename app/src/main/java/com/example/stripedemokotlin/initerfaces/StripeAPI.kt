package com.example.stripedemokotlin.initerfaces

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*
import java.util.HashMap

interface StripeAPI {

    @Headers(
        "Authorization: Bearer sk_test_qabOuoz1VwS1lVO2QjzFMBCh00kmAcPwhT",
        "Content-Type: application/x-www-form-urlencoded"
    )
    @POST("/v1/customers/cus_FOj9wjteKYaxZg/sources")
    @FormUrlEncoded
    suspend fun addCard(@FieldMap map: Map<String,@JvmSuppressWildcards Any>): Response<ResponseBody>

    @Headers(
        "Authorization: Bearer sk_test_qabOuoz1VwS1lVO2QjzFMBCh00kmAcPwhT",
        "Content-Type: application/x-www-form-urlencoded"
    )
    @GET("/v1/customers/cus_FOj9wjteKYaxZg/sources")
    suspend fun getAllCards(@QueryMap msp: Map<String, String>): Response<ResponseBody>


    @Headers(
        "Authorization: Bearer sk_test_qabOuoz1VwS1lVO2QjzFMBCh00kmAcPwhT",
        "Content-Type: application/x-www-form-urlencoded"
    )
    @POST("/v1/customers/cus_FOj9wjteKYaxZg/sources/{id}")
    @FormUrlEncoded
    suspend fun updateCard(@Path("id") id: String, @FieldMap map: Map<String,@JvmSuppressWildcards Any>): Response<ResponseBody>


    @Headers("Authorization: Bearer sk_test_qabOuoz1VwS1lVO2QjzFMBCh00kmAcPwhT")
    @DELETE("/v1/customers/cus_FOj9wjteKYaxZg/sources/{id}")
    suspend fun deleteCard(@Path("id") id: String): Response<ResponseBody>

}