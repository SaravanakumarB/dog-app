package com.example.dog_app.data.ds

import com.example.dog_app.data.response.GetAllDogMainResponse
import com.example.dog_app.data.utils.GsonUtil
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import java.lang.reflect.Type

class GetAllDogMainResponseDeserializer : JsonDeserializer<GetAllDogMainResponse> {
    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): GetAllDogMainResponse {
        return GsonUtil.getInstance().kJsonDeserialization(GetAllDogMainResponse(), json)
    }
}