package id.kharozim.firechat.services

import id.kharozim.firechat.models.PayloadModelBody
import id.kharozim.firechat.models.ResponseModel
import id.kharozim.firechat.utils.Constant
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface NotifService {
    @POST("send")
    @Headers(
        "Content-Type:application/json",
        "Authorization:key=${Constant.API_KEY}"
    )
    suspend fun sendNotification(@Body body: PayloadModelBody): ResponseModel
}