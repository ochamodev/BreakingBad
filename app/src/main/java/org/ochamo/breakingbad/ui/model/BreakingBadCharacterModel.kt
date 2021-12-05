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
)

