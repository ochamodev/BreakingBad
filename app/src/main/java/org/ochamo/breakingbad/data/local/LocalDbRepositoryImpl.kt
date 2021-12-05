package org.ochamo.breakingbad.data.local

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import org.ochamo.breakingbad.data.DataResult
import org.ochamo.breakingbad.data.local.entity.BreakingBadFavoriteItem
import javax.inject.Inject

class LocalDbRepositoryImpl @Inject constructor(
    val breakingBadDao: BreakingBadDao,
    val ioDispatcher: CoroutineDispatcher
) : LocalDbRepository {

    override suspend fun getFavoriteItems(): DataResult<HashMap<Int,BreakingBadFavoriteItem>> = withContext(ioDispatcher) {
        try {
            val favorites = breakingBadDao.getAll()
            val hashMap = HashMap<Int, BreakingBadFavoriteItem>()

            favorites.forEach {
                hashMap[it.id] = it
            }
            return@withContext DataResult(hashMap, null)
        } catch (e: Exception) {
            return@withContext DataResult(null, e)
        }

    }

    override suspend fun saveFavorite(item: BreakingBadFavoriteItem) {
        breakingBadDao.insertFavorite(item)
    }

    override suspend fun deleteFavorite(item: BreakingBadFavoriteItem) {
        breakingBadDao.deleteFavorite(item)
    }
}