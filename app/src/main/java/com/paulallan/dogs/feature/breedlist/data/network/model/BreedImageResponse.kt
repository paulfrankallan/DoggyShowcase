package com.paulallan.dogs.feature.breedlist.data.network.model

import com.paulallan.dogs.core.network.BaseResponse

data class BreedImageResponse(
    val message: String,
    override val status: String
) : BaseResponse