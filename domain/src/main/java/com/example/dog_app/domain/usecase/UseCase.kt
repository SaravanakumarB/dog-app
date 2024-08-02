package com.example.dog_app.domain.usecase

abstract class UseCase<INPUT_TYPE, OUTPUT_TYPE> {
  abstract fun execute(params: INPUT_TYPE? = null): OUTPUT_TYPE
}
