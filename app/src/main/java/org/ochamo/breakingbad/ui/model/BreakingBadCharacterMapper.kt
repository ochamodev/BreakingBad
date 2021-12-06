package org.ochamo.breakingbad.ui.model

import androidx.databinding.ObservableBoolean
import org.ochamo.breakingbad.data.local.entity.BreakingBadFavoriteItem
import org.ochamo.breakingbad.data.model.BreakingBadCharacter

class BreakingBadCharacterMapper {
    fun map(items: ArrayList<BreakingBadCharacter>, favorites: HashMap<Int, BreakingBadFavoriteItem>): ArrayList<BreakingBadCharacterModel> {
        return items.map {
            map(it, favorites)
        } as ArrayList<BreakingBadCharacterModel>
    }

    fun map(item: BreakingBadCharacter, favorites: HashMap<Int, BreakingBadFavoriteItem>): BreakingBadCharacterModel {

        val favoriteItem = favorites[item.id]
        var isFavorite = false
        if (favoriteItem != null) {
            isFavorite = favoriteItem.isFavorite
        }
        return BreakingBadCharacterModel(
            id = item.id,
            name = item.name,
            nickname = item.nickname,
            img = item.img,
            birthday = item.birthday,
            occupation = item.occupation,
            status = item.status,
            appearance = item.appearance,
            portrayed = item.portrayed,
            category = item.category,
            isFavorite = ObservableBoolean(isFavorite)
        )
    }

}