package com.paulallan.dogs.feature.breedlist.data.network.model

import com.paulallan.dogs.core.network.BaseResponse

data class BreedListResponse(
    val message: Map<String, List<String>>,
    override val status: String
): BaseResponse
