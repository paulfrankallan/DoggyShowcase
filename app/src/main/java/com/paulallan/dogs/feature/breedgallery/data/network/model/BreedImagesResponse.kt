package com.paulallan.dogs.feature.breedgallery.data.network.model

import com.paulallan.dogs.core.network.BaseResponse

data class BreedImagesResponse(
    val message: List<String>,
    override val status: String
) : BaseResponse