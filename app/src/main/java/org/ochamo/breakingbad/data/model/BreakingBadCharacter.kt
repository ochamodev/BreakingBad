package org.ochamo.breakingbad.data.model

import com.google.gson.annotations.SerializedName


data class BreakingBadCharacter(
    @SerializedName("char_id")
    val id: Int,
    val name: String,
    val birthday: String,
    val occupation: ArrayList<String>,
    val img: String,
    val status: String,
    val nickname: String,
    val appearance: ArrayList<String>,
    val portrayed: String,
    val category: String
)