package org.ochamo.breakingbad.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import org.ochamo.breakingbad.data.local.entity.BreakingBadFavoriteItem

@Dao
interface BreakingBadDao {

    @Query("SELECT * FROM BreakingBadFavoriteItem")
    fun getAll(): List<BreakingBadFavoriteItem>

    @Insert
    suspend fun insertFavorite(newFavorite: BreakingBadFavoriteItem)

    @Delete
    suspend fun deleteFavorite(favorite: BreakingBadFavoriteItem)

}