package id.kharozim.firechat.clients

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import id.kharozim.firechat.services.NotifService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object NotifClient {

    private const val BASE_URL = "https://fcm.googleapis.com/fcm/"
    val service: NotifService by lazy {

        val gson: Gson by lazy {GsonBuilder().setLenient().create()}
        val interceptor: HttpLoggingInterceptor by lazy {HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)}
        val httpClient: OkHttpClient by lazy {OkHttpClient.Builder().addInterceptor(interceptor).build()}
        val retrofit: Retrofit by lazy {
            Retrofit
                .Builder()
                .baseUrl(BASE_URL)
                .client(httpClient)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()
        }
        retrofit.create(NotifService::class.java)
    }
}