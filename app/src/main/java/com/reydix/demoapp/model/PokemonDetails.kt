package com.reydix.demoapp

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize


data class Pokemon(

    var baseExperience: Int? = null,
    var height: Int? = null,
    var heldItems: ArrayList<String> = arrayListOf(),
    var id: Int? = null,
    var isDefault: Boolean? = null,
    var locationAreaEncounters: String? = null,
    var name: String? = null,
    var order: Int? = null,
    var pastTypes: ArrayList<String> = arrayListOf(),
    var species: Species,
    var sprites: Sprites,
    var stats: ArrayList<Stats> = arrayListOf(),
    var weight: Int? = null,
    var we: Boolean

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
