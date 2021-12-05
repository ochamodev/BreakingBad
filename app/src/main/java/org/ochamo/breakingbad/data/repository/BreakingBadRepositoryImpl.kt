package org.ochamo.breakingbad.data.repository

import android.util.Log
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import org.ochamo.breakingbad.data.DataResult
import org.ochamo.breakingbad.data.model.BreakingBadCharacter
import org.ochamo.breakingbad.data.network.BreakingBadService
import retrofit2.Response
import java.lang.reflect.Type

class BreakingBadRepositoryImpl(
    val breakingBadService: BreakingBadService,
    val ioDispatcher: CoroutineDispatcher
) : BreakingBadRepository {
    override suspend fun getTasks(
        limit: Int,
        offset: Int
    ): DataResult<ArrayList<BreakingBadCharacter>> = withContext(ioDispatcher) {
        try {
            val result = breakingBadService.getPaginatedCharacters(limit, offset)
            if (result.isSuccessful) {
                val gson = Gson()
                val type: Type = object: TypeToken<ArrayList<BreakingBadCharacter>>() {} .type
                val data = gson.fromJson<ArrayList<BreakingBadCharacter>>(result.body()!!, type)
                DataResult(data, null)
            } else {
                DataResult(null, Exception("Ha ocurrido un error en la peticion"))
            }
        } catch (exception: Exception) {
            Log.d("*****BUGATTI", exception.message!!)
            DataResult(null, exception)
        }
    }
}