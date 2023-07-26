package com.reydix.demoapp.model.popularPokemon

data class Response(
    var results: List<Result>
)


data class Result(
    var name: String,
    var url: String,
)