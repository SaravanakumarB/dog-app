package com.example.dog_app.data.mappers

import com.example.dog_app.data.response.GetAllDogMainResponse
import com.example.dog_app.data.response.GetImagesResponse
import com.example.dog_app.data.response.GetRandomImageResponse
import com.example.dog_app.domain.response.GetAllDogMainModel
import com.example.dog_app.domain.response.GetImagesModel
import com.example.dog_app.domain.response.GetRandomImageModel
import org.mapstruct.Mapper

@Mapper
interface DogMapper {
    fun convertAllDogDataToDomainModel(getAllDogMainResponse : GetAllDogMainResponse): GetAllDogMainModel

    fun convertBreedImagesDataToDomainModel(getImagesResponse: GetImagesResponse): GetImagesModel

    fun convertRandomImageDataToDomainModel(getRandomImageResponse: GetRandomImageResponse) : GetRandomImageModel
}