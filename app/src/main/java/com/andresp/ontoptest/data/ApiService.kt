package com.andresp.ontoptest.data

import com.andresp.ontoptest.presentation.model.CharactersResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("character")
    suspend fun getCharacters(
        @Query("page") page: Int
    ): CharactersResponse
}
