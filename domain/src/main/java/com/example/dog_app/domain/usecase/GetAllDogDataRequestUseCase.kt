package com.example.dog_app.domain.usecase

import com.example.dog_app.domain.datasources.DogDataSources
import com.example.dog_app.domain.response.GetAllDogMainModel
import io.reactivex.Single
import javax.inject.Inject

class GetAllDogDataRequestUseCase @Inject constructor(private val dataSources: DogDataSources) :
    UseCase<GetAllDogDataRequestUseCase.Params?, Single<GetAllDogMainModel>>() {
    override fun execute(params: Params?): Single<GetAllDogMainModel> {
        return dataSources.getAllDogDataRequest()
    }

    class Params() : BaseParams
}