package com.reydix.demoapp.presentation.main

import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.palette.graphics.Palette
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.reydix.demoapp.model.Stats
import com.reydix.demoapp.databinding.ActivityPokemonDetailsBinding
import com.reydix.demoapp.presentation.main.adapters.ProgressBarAdapter
import java.util.Locale


class PokemonDetailActivity : AppCompatActivity() {
    private lateinit var binding : ActivityPokemonDetailsBinding
    private lateinit var adapter: ProgressBarAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPokemonDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRecyclerView()
        initializeAdapter()
        initializeViews()
    }
    private fun initializeAdapter() {
        adapter = ProgressBarAdapter(arrayListOf())
        binding.progressRecycler.adapter = adapter
    }
    private fun initializeViews() {
        var name = intent.getStringExtra("PokemonName")
        val pokemonImage = intent.getStringExtra("PokemonImage")
        val statsList = intent.getSerializableExtra("StatsList") as? ArrayList<Stats>

        binding.pokemonName.text = name?.replaceFirstChar {
            if (it.isLowerCase()) it.titlecase(
                Locale.getDefault()
            ) else it.toString()
        }

        Glide.with(binding.pokemonImage)
            .asBitmap()
            .load(pokemonImage)
            .apply(RequestOptions().override(800, 800))
            .into(object : CustomTarget<Bitmap>() {
                override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                    val dominantColor = getDominantColor(resource)
                    val darkenColor = adjustColorBrightness(dominantColor, 0.3f)
                    binding.cardView.setCardBackgroundColor(darkenColor)
                    binding.pokemonImage.setImageBitmap(resource)
                }

                override fun onLoadCleared(placeholder: Drawable?) {
                }
            })

        if (statsList != null) loadResults(statsList)
    }
    private fun loadResults(statsList: ArrayList<Stats>) {
        adapter.updateList(statsList)
    }

    private fun setupRecyclerView() {
        binding.progressRecycler.layoutManager = LinearLayoutManager(this)
        binding.progressRecycler.setHasFixedSize(false)
    }
    private fun getDominantColor(bitmap: Bitmap): Int {
        val palette = Palette.from(bitmap).generate()
        return palette.getDominantColor(0)
    }

    fun adjustColorBrightness(color: Int, factor: Float): Int {
        val a = Color.alpha(color)
        val r = (Color.red(color) * factor).toInt()
        val g = (Color.green(color) * factor).toInt()
        val b = (Color.blue(color) * factor).toInt()
        return Color.argb(a, clamp(r), clamp(g), clamp(b))
    }

    private fun clamp(value: Int): Int {
        return when {
            value < 0 -> 0
            value > 255 -> 255
            else -> value
        }
    }

}