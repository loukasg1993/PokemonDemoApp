package com.reydix.demoapp.presentation.main.adapters

import android.animation.ObjectAnimator
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.palette.graphics.Palette
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.reydix.demoapp.model.Pokemon
import com.reydix.demoapp.R
import com.reydix.demoapp.databinding.PokemonCardItemBinding
import com.reydix.demoapp.presentation.main.PokemonDetailActivity
import java.util.Locale

class ResultAdapter(private var pokemons: ArrayList<Pokemon>) :
    RecyclerView.Adapter<ResultAdapter.ResultViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ResultViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = PokemonCardItemBinding.inflate(inflater, parent, false)
        return ResultViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ResultViewHolder, position: Int) {
        val result = pokemons[position]
        holder.bind(result)
    }

    override fun getItemCount() = pokemons.size

    fun updatePokemonList(pokemonList: List<Pokemon>) {
        val diffCallback = ResultDiffCallback(pokemons, pokemonList)
        val diffResult = DiffUtil.calculateDiff(diffCallback)

        pokemons.clear()
        pokemons.addAll(pokemonList)

        diffResult.dispatchUpdatesTo(this)
    }

    inner class ResultViewHolder(private val binding: PokemonCardItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(pokemon: Pokemon) {
            binding.name.text = pokemon.species.name.replaceFirstChar {
                if (it.isLowerCase()) it.titlecase(
                    Locale.getDefault()
                ) else it.toString()
            }

            Glide.with(binding.pokemonImage.context).asBitmap()
                .load(pokemon.sprites.other.home.front_default)
                .placeholder(R.drawable.placeholder)
                .error(R.drawable.placeholder)
                .diskCacheStrategy(DiskCacheStrategy.DATA)
                .into(object : CustomTarget<Bitmap>() {
                override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                    val dominantColor = getDominantColor(resource)
                    val lighterColor = adjustColorBrightness(dominantColor, 0.3f)
                    binding.cardView.setCardBackgroundColor(lighterColor)
                    binding.pokemonImage.setImageBitmap(resource)
                }

                override fun onLoadCleared(placeholder: Drawable?) {
                }
            })

            binding.cardView.setOnClickListener {
                var intent = Intent(itemView.context, PokemonDetailActivity::class.java)

                intent.putExtra("StatsList", pokemon.stats)
                intent.putExtra("PokemonImage", pokemon.sprites.other.home.front_default)
                intent.putExtra("PokemonName", pokemon.species.name)

                itemView.context.startActivity(intent)
            }
            binding.cardView.animation =
                AnimationUtils.loadAnimation(itemView.context, R.anim.pokemon_recycler_item_anim)
        }


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
