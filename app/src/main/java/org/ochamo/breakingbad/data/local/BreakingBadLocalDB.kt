package org.ochamo.breakingbad.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import org.ochamo.breakingbad.data.local.entity.BreakingBadFavoriteItem

@Database(entities = [BreakingBadFavoriteItem::class], version = 1)
abstract class BreakingBadLocalDB : RoomDatabase() {

    abstract fun breakingBadDao(): BreakingBadDao

}