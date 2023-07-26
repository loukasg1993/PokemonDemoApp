package com.reydix.demoapp.api

import com.reydix.demoapp.Pokemon
import com.reydix.demoapp.model.popularPokemon.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface ApiService {
    @GET("pokemon/")
    suspend fun getPokemonList(@Query("limit") limit: Int): Response

    @GET("pokemon/{id}/")
    suspend fun getPokemonDescription(@Path("id") pokemonId: Int): Pokemon

}