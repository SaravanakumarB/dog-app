package com.example.dog_app.domain.usecase

import com.example.dog_app.domain.datasources.DogDataSources
import com.example.dog_app.domain.response.GetRandomImageModel
import io.reactivex.Single
import javax.inject.Inject

class GetRandomImageRequestUseCase @Inject constructor(private val dataSources: DogDataSources) :
    UseCase<GetRandomImageRequestUseCase.Params?, Single<GetRandomImageModel>>() {
    override fun execute(params: Params?): Single<GetRandomImageModel> {
        return dataSources.getRandomImage()
    }

    class Params() : BaseParams
}