package org.ochamo.breakingbad.data.local

import kotlinx.coroutines.CoroutineDispatcher
import org.ochamo.breakingbad.data.DataResult
import org.ochamo.breakingbad.data.local.entity.BreakingBadFavoriteItem

interface LocalDbRepository {

    suspend fun getFavoriteItems(): DataResult<HashMap<Int,BreakingBadFavoriteItem>>

    suspend fun saveFavorite(item: BreakingBadFavoriteItem)

    suspend fun deleteFavorite(item: BreakingBadFavoriteItem)

}