package com.paulallan.dogs.app.presentation.mvi

interface Intent

class DummyClassIntent(val url: String) : Intent
object DummyObjectIntent : Intent
