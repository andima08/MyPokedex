package com.example.mypokedex.data.response

import com.google.gson.annotations.SerializedName
data class ListPokemonDetail(
	@field:SerializedName("abilities")
	val abilities: List<AbilitiesItem?>? = null,
	@field:SerializedName("sprites")
	val sprites: Sprites? = null //
)

data class Sprites(
	@field:SerializedName("front_default")
	val frontDefault: String? = null,

	@field:SerializedName("front_shiny")
	val frontShiny: String? = null,
)
data class Abilities(
	@field:SerializedName("name")
	val name: String? = null,
	@field:SerializedName("url")
	val url: String? = null
)
data class AbilitiesItem(
	@field:SerializedName("is_hidden")
	val isHidden: Boolean? = null,
	@field:SerializedName("ability")
	val ability: Abilities? = null,
	@field:SerializedName("slot")
	val slot: Int? = null
)
