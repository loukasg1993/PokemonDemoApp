package com.reydix.demoapp.presentation.main

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Bundle
import android.os.Parcelable
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.reydix.demoapp.presentation.main.adapters.ResultAdapter
import com.reydix.demoapp.R
import com.reydix.demoapp.databinding.ActivityMainBinding
import com.reydix.demoapp.presentation.main.adapters.EventAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel


class MainActivity : AppCompatActivity() {
    private val viewModel: MainViewModel by viewModel()
    private lateinit var adapter: ResultAdapter
    private lateinit var eventAdapter: EventAdapter
    private lateinit var binding : ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRecyclerViews()
        initializeAdapters()
        subscribeObservers()

        if(savedInstanceState == null){
                fetchEventData()
                fetchData()
        }
    }
    private fun initializeAdapters() {
        adapter = ResultAdapter(arrayListOf())
        binding.popularPokemonRecycler.adapter = adapter

        eventAdapter = EventAdapter(arrayListOf())
        binding.eventsRecycler.adapter = eventAdapter
    }
    private fun fetchData() {
        viewModel.fetchData()
    }
    private fun fetchEventData() {
        viewModel.fetchEvents()
    }

    private fun subscribeObservers(){

        viewModel.pokemonResults.observe(this) { pokemonResults ->
            adapter.updatePokemonList(pokemonResults)
            binding.progressBar.visibility = View.GONE
            binding.popularPokemonTxt.text = resources.getString(R.string.popular_pokemon)
            binding.mainActivityLinearLayout.visibility = View.VISIBLE
        }

        viewModel.errorMessage.observe(this) { errorMessage ->
            if (!errorMessage.isNullOrEmpty()) {
                binding.progressBar.visibility = View.GONE
                showErrorDialog(errorMessage)
            }
        }

        viewModel.eventResults.observe(this) { eventResults ->
            eventAdapter.updateEventList(eventResults)
            binding.featuredThisWeekTxt.text = resources.getString(R.string.this_week)
        }

    }
    private fun showErrorDialog(errorMessage: String) {
        AlertDialog.Builder(this)
            .setTitle("Error")
            .setMessage(errorMessage)
            .setPositiveButton("OK") { dialog, _ -> dialog.dismiss() }
            .show()
    }
    private fun setupRecyclerViews() {
        binding.popularPokemonRecycler.layoutManager = LinearLayoutManager(this, RecyclerView.HORIZONTAL, false)
        binding.popularPokemonRecycler.setHasFixedSize(false)
        binding.popularPokemonRecycler.itemAnimator = null

        binding.eventsRecycler.layoutManager = LinearLayoutManager(this, RecyclerView.HORIZONTAL, false)
        binding.eventsRecycler.setHasFixedSize(false)
        binding.eventsRecycler.itemAnimator = null
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        // Save the state of the RecyclerView here for landscape orientation
        val layoutManager = binding.popularPokemonRecycler.layoutManager
        val state = layoutManager?.onSaveInstanceState()
        outState.putParcelable(KEY_RECYCLER_STATE, state)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        val layoutManager = binding.popularPokemonRecycler.layoutManager
        val state = savedInstanceState.getParcelable<Parcelable>(KEY_RECYCLER_STATE)
        layoutManager?.onRestoreInstanceState(state)
    }

    companion object {
        private const val KEY_RECYCLER_STATE = "recycler_state"
    }

}