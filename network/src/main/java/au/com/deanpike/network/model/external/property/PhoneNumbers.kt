package au.com.deanpike.network.model.external.property

import com.google.gson.annotations.SerializedName

data class PhoneNumbers(
    @SerializedName("type") var type: String? = null,
    @SerializedName("display_label") var displayLabel: String? = null,
    @SerializedName("number") var number: String? = null
)