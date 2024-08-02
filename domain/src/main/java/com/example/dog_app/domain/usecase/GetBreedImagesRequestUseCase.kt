package com.example.dog_app.domain.usecase

import com.example.dog_app.domain.datasources.DogDataSources
import com.example.dog_app.domain.response.GetImagesModel
import io.reactivex.Single
import javax.inject.Inject

class GetBreedImagesRequestUseCase @Inject constructor(private val dataSources: DogDataSources) :
    UseCase<GetBreedImagesRequestUseCase.Params?, Single<GetImagesModel>>() {
    override fun execute(params: Params?): Single<GetImagesModel> {
        return dataSources.getBreedsImages(params?.breed ?: "")
    }

    class Params(var breed: String) : BaseParams
}