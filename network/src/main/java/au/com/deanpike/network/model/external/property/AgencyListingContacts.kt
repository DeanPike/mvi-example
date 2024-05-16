package au.com.deanpike.network.model.external.property

import com.google.gson.annotations.SerializedName

data class AgencyListingContacts(
    @SerializedName("agent_id") var agentId: String? = null,
    @SerializedName("address") var address: String? = null,
    @SerializedName("first_name") var firstName: String? = null,
    @SerializedName("last_name") var lastName: String? = null,
    @SerializedName("display_full_name") var displayFullName: String? = null,
    @SerializedName("image_url") var imageUrl: String? = null,
    @SerializedName("email_address") var emailAddress: String? = null,
    @SerializedName("phone_numbers") var phoneNumbers: List<PhoneNumbers> = emptyList(),
    @SerializedName("domain_url") var domainUrl: String? = null
)