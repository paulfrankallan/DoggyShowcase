package com.paulallan.dogs.core.outcome

sealed class Outcome<out T> {
    data class Success<out T>(val data: T) : Outcome<T>()
    data class Error(val message: String) : Outcome<Nothing>()
}