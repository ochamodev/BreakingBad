package org.ochamo.breakingbad.ui.model

import org.ochamo.breakingbad.data.model.BreakingBadCharacter

class BreakingBadCharacterMapper {
    fun map(items: ArrayList<BreakingBadCharacter>): ArrayList<BreakingBadCharacterModel> {
        return items.map {
            map(it)
        } as ArrayList<BreakingBadCharacterModel>
    }

    fun map(item: BreakingBadCharacter): BreakingBadCharacterModel {
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
            isFavorite = false
        )
    }

}