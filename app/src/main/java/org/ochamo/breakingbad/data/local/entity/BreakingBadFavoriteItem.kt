package org.ochamo.breakingbad.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity
data class BreakingBadFavoriteItem(
    @PrimaryKey val id: Int,
    @ColumnInfo(name = "isFavorite") val isFavorite: Boolean
)