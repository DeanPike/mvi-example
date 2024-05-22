package au.com.deanpike.network.model.external.detail

import com.google.gson.annotations.SerializedName

data class CompletionDate(
    @SerializedName("formatted_completion_date")
    val formattedCompletionDate: String,
    @SerializedName("estimated_completion_date")
    val estimatedCompletionDate: String,
)
