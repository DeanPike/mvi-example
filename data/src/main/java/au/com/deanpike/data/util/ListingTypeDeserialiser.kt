package au.com.deanpike.data.util

import au.com.deanpike.client.model.listing.response.ListingType
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import java.lang.reflect.Type

class ListingTypeDeserialiser : JsonDeserializer<ListingType> {
    override fun deserialize(json: JsonElement?, typeOfT: Type?, context: JsonDeserializationContext?): ListingType? {
        val types: Array<ListingType> = ListingType.values()
        for (type in types) {
            if (type.value == json!!.asString) return type
        }

        return null
    }
}