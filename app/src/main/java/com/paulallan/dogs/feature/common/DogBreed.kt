package com.paulallan.dogs.feature.common

data class DogBreed(
    val name: String,
    val imageUrl: String
) {
    override fun toString(): String {
        return "Dog(name='$name', imageUrl='$imageUrl')"
    }
}
