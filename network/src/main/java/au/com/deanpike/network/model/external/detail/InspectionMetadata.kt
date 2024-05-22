package au.com.deanpike.network.model.external.detail

import com.google.gson.annotations.SerializedName

data class InspectionMetadata(
    @SerializedName("inspection_planner")
    val inspectionPlanner: Boolean,
    val title: String,
    val cta: List<CTA>,
)
