package org.ochamo.breakingbad.data.network

import com.google.gson.JsonArray
import org.ochamo.breakingbad.data.model.BreakingBadCharacter
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface BreakingBadService {
    @Headers("Content-Type: application/json")
    @GET(value = "/api/characters")
    suspend fun getPaginatedCharacters(
        @Query(value = "limit") limit: Int,
        @Query(value = "offset") offset: Int
    ): Response<JsonArray>

}