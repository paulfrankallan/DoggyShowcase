package com.paulallan.dogs.core.network

interface BaseResponse {
    val status: String
}

@Suppress("UNCHECKED_CAST")
fun <T> BaseResponse.requireSuccess(data: T): T {
    if (status != "success") {
        throw IllegalStateException("API error status: $status")
    }

    return data
}