package com.reydix.demoapp.api

import com.reydix.demoapp.Pokemon
import com.reydix.demoapp.model.popularPokemon.Response


class Repository(private val apiService: ApiService) {

    suspend fun getPokemonList(limit: Int) : Response {
        return apiService.getPokemonList(limit)
    }

    suspend fun getPokemonDescription(id: Int) : Pokemon {
        return apiService.getPokemonDescription(id)
    }


}