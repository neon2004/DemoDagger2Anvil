package com.example.demo.data.api


import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Url

interface ApiService {

    @GET  fun getData(@Url url: String): Call<String> // TODO cambiar cuando tengamos un servicio

}