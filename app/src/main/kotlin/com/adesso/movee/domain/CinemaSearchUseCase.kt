package com.adesso.movee.domain

import com.adesso.movee.data.remote.model.cinema.OSMObject
import com.adesso.movee.data.repository.CinemaRepository
import com.adesso.movee.internal.util.UseCase
import javax.inject.Inject

class CinemaSearchUseCase @Inject constructor(
    private val repository: CinemaRepository
): UseCase<List<OSMObject>,String>() {
    override suspend fun buildUseCase(params: String) = repository.getCinemas(params)
}
