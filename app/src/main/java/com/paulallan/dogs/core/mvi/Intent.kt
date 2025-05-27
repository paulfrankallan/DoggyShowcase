package com.paulallan.dogs.core.mvi

interface Intent

class DummyClassIntent(val url: String) : Intent
object DummyObjectIntent : Intent
