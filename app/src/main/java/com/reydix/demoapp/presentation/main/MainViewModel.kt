package com.reydix.demoapp.presentation.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.reydix.demoapp.Pokemon
import com.reydix.demoapp.api.Repository
import com.reydix.demoapp.model.Event


import kotlinx.coroutines.launch


class MainViewModel(private val repository: Repository) : ViewModel() {
    private var events = arrayListOf<Event>()
    private val pokemonList: MutableList<Pokemon> = mutableListOf()

    private val _pokemonResults = MutableLiveData<List<Pokemon>>()
    val pokemonResults: LiveData<List<Pokemon>>
    get() = _pokemonResults
    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String>
    get() = _errorMessage
    private val _eventResults = MutableLiveData<ArrayList<Event>>()
    val eventResults: LiveData<ArrayList<Event>>
        get() = _eventResults
    fun fetchData() {
        viewModelScope.launch {
            try {
                val response = repository.getPokemonList(12)
                for (pokemon in response.results) {
                    var url = pokemon.url
                    val idPattern = "/(\\d+)/$".toRegex()
                    val matchResult = idPattern.find(url)
                    val id = matchResult?.groupValues?.getOrNull(1)!!.toInt()

                    // Make API call for each Pokemon with the extracted ID
                    val pokemonDetails = repository.getPokemonDescription(id)
                    pokemonDetails.we = false
                    pokemonList.add(pokemonDetails)
                }

                _pokemonResults.postValue(pokemonList)

            } catch (e: Exception) {
                _errorMessage.value = e.message
            }
        }
    }

    fun fetchEvents() {
        viewModelScope.launch {
            try {
                // Here we would make the actual API call
//                val events = repository.getEventsData()
                events = getEventsFromNetwork()
                _eventResults.postValue(events)

            } catch (e: Exception) {
                _errorMessage.value = e.message
            }
        }
    }
    private fun getEventsFromNetwork(): ArrayList<Event> {
        events.add(Event("Feed 10 Berries at Gyms.", "Canalave City", "Tue 1 Aug", "picture1"))
        events.add(Event("Defend Gyms for 3 hours.", "Eterna City", "Wed 2 Aug", "picture2"))
        events.add(Event("A Drive to Investigate.", "Oreburgh Mine", "Thu 3 Aug", "picture3"))
        events.add(Event("Season of Alola Partner.", "Route 214", "Fri 4 Aug", "picture4"))
        events.add(Event("Season of Light.", "Wayward Cave", "Fri 4 Aug", "picture5"))
        events.add(Event("Collect Vivillon.", "Lost Tower", "Sat 5 Aug", "picture6"))
        return events
    }

}