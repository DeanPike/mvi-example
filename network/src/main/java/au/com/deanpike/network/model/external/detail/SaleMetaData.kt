package au.com.deanpike.network.model.external.detail

import com.google.gson.annotations.SerializedName

data class SaleMetaData(
    @SerializedName("date_sold") val dateSold: String? = null,
    @SerializedName("sale_type") val saleType: String? = null
)