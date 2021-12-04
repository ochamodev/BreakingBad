package org.ochamo.breakingbad.ui.model

data class BreakingBadCharacterModel(
    val id: Int,
    val name: String,
    val birthday: String,
    val occupation: ArrayList<String>,
    val img: String,
    val status: String,
    val nickname: String,
    val appearance: String,
    val portrayed: String,
    val category: ArrayList<String>,
    var isFavorite: Boolean
)

