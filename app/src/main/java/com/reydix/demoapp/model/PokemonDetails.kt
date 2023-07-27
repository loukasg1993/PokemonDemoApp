package com.reydix.demoapp.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

data class Pokemon(
    var id: Int? = null,
    var baseExperience: Int? = null,
    var height: Int? = null,
    var isDefault: Boolean? = null,
    var locationAreaEncounters: String? = null,
    var name: String? = null,
    var order: Int? = null,
    var species: Species,
    var sprites: Sprites,
    var stats: ArrayList<Stats> = arrayListOf(),
    var weight: Int? = null,

)

data class Species(
    var name:String
)

data class Sprites(
    var other: Other,
    var versions: Versions
)

@Parcelize
data class Stats(
    var base_stat: Int,
    var stat: Stat
) : Parcelable

data class Other(
    var home: Home,
)

data class Home(
    var front_default : String,
    var front_shiny : String,

    )

data class Versions(
    @SerializedName("generation-v")
    var generationV: GenerationV
)

data class GenerationV(
    @SerializedName ("black-white")
    var blackWhite: BlackWhite
)

data class BlackWhite(
    var animated: Animated
)

data class Animated(
    var front_default: String
)

@kotlinx.android.parcel.Parcelize
data class Stat(
    var name : String,
) : Parcelable
