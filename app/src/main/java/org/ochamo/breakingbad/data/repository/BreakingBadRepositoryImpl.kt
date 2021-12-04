package org.ochamo.breakingbad.data.repository

import com.google.gson.Gson
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import org.ochamo.breakingbad.data.DataResult
import org.ochamo.breakingbad.data.model.BreakingBadCharacter
import org.ochamo.breakingbad.data.network.BreakingBadService
import retrofit2.Response

class BreakingBadRepositoryImpl(
    val breakingBadService: BreakingBadService,
    val ioDispatcher: CoroutineDispatcher
) : BreakingBadRepository {
    override suspend fun getTasks(
        limit: Int,
        offset: Int
    ): DataResult<ArrayList<BreakingBadCharacter>> = withContext(ioDispatcher) {
        val result = breakingBadService.getPaginatedCharacters(limit, offset)
        if (result.isSuccessful) {
            val response = result.body()
            DataResult(response, null)
        } else {
            DataResult(null, Exception("Ha ocurrido un error en la peticion"))
        }
    }
}