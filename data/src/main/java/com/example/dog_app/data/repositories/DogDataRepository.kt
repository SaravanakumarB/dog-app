package com.example.dog_app.data.repositories

import com.example.dog_app.data.IService
import com.example.dog_app.data.mappers.DogMapper
import com.example.dog_app.domain.datasources.DogDataSources
import com.example.dog_app.domain.response.GetAllDogMainModel
import com.example.dog_app.domain.response.GetImagesModel
import com.example.dog_app.domain.response.GetRandomImageModel
import io.reactivex.Single
import org.mapstruct.factory.Mappers
import javax.inject.Inject

class DogDataRepository @Inject constructor(private val service: IService) : DogDataSources {

    private val mapper = Mappers.getMapper(DogMapper::class.java)

    override fun getAllDogDataRequest(): Single<GetAllDogMainModel> {
        return service.getAllDogList().map {
            mapper.convertAllDogDataToDomainModel(it)
        }
    }

    override fun getBreedsImages(breed: String): Single<GetImagesModel> {
        return service.getBreedImages(breed).map {
            mapper.convertBreedImagesDataToDomainModel(it)
        }
    }

    override fun getRandomImage(): Single<GetRandomImageModel> {
        return service.getRandomImage().map {
            mapper.convertRandomImageDataToDomainModel(it)
        }
    }
}