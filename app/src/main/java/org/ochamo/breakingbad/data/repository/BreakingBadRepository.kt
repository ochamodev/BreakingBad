package org.ochamo.breakingbad.data.repository

import org.ochamo.breakingbad.data.DataResult
import org.ochamo.breakingbad.data.model.BreakingBadCharacter

interface BreakingBadRepository {

    suspend fun getTasks(limit: Int, offset: Int): DataResult<ArrayList<BreakingBadCharacter>>

}