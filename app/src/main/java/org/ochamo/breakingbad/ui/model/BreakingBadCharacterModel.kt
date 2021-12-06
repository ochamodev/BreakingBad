package org.ochamo.breakingbad.ui.model

import androidx.databinding.ObservableBoolean

data class BreakingBadCharacterModel(
    val id: Int,
    val name: String,
    val birthday: String,
    val occupation: ArrayList<String>,
    val img: String,
    val status: String,
    val nickname: String,
    val appearance: ArrayList<String>,
    val portrayed: String,
    val category: String,
    var isFavorite: ObservableBoolean
) {
    override fun hashCode(): Int {
        return id.hashCode()
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as BreakingBadCharacterModel

        if (id != other.id) return false
        if (name != other.name) return false
        if (birthday != other.birthday) return false
        if (occupation != other.occupation) return false
        if (img != other.img) return false
        if (status != other.status) return false
        if (nickname != other.nickname) return false
        if (appearance != other.appearance) return false
        if (portrayed != other.portrayed) return false
        if (category != other.category) return false

        return true
    }
}

