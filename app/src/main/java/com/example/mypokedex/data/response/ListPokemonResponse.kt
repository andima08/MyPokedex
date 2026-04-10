package com.example.mypokedex.data.response

import com.google.gson.annotations.SerializedName
import android.os.Parcelable

data class ListPokemonResponse(
	@field:SerializedName("next")
	val next: String? = null,
	@field:SerializedName("previous")
	val previous: Any? = null,
	@field:SerializedName("count")
	val count: Int? = null,
	@field:SerializedName("results")
	val results: List<ResultsItem?>? = null
)

data class ResultsItem(
	@field:SerializedName("name")
	val name: String? = null,
	@field:SerializedName("url")
	val url: String? = null
)
