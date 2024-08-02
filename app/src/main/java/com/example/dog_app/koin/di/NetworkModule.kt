package com.example.dog_app.koin.di

import com.example.dog_app.BuildConfig
import com.example.dog_app.authhelper.NetworkAuthInterceptor
import com.example.dog_app.data.IService
import com.example.dog_app.data.utils.GsonUtil
import com.example.dog_app.data.utils.RequestInterceptor
import com.example.dog_app.errorhandling.APIExceptionMapper
import com.example.dog_app.errorhandling.IAPIExceptionMapper
import com.example.dog_app.errorhandling.RxErrorHandlingCallAdapterFactory
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Protocol
import okhttp3.logging.HttpLoggingInterceptor

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

  @Provides
  @Singleton
  fun provideRequestInterceptor(
  ): RequestInterceptor {
    return RequestInterceptor()
  }

  @Provides
  @Singleton
  fun getInterceptorList(
    requestInterceptor: RequestInterceptor,
    NetworkAuthInterceptor: NetworkAuthInterceptor
  ): MutableList<@JvmSuppressWildcards Interceptor> {
    val interceptorList = ArrayList<Interceptor>()
    interceptorList.add(requestInterceptor)
    interceptorList.add(NetworkAuthInterceptor)
    val loggingInterceptor = HttpLoggingInterceptor()
    if (BuildConfig.DEBUG) {
      loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
    } else {
      loggingInterceptor.level = HttpLoggingInterceptor.Level.NONE
    }
    interceptorList.add(loggingInterceptor)
    return interceptorList.toMutableList()
  }

  @Provides
  @Singleton
  fun provideAPIExceptionMapper(): IAPIExceptionMapper {
    return APIExceptionMapper()
  }

  @Provides
  @Singleton
  fun provideOkHttpClient(interceptorList: MutableList<Interceptor>): OkHttpClient {
    var builder = OkHttpClient.Builder()
    builder.protocols(listOf(Protocol.HTTP_1_1))
    builder.interceptors().addAll(interceptorList)

    builder = builder.connectTimeout(CONNECTION_TIMEOUT_SEC.toLong(), TimeUnit.SECONDS)
    builder = builder.writeTimeout(WRITE_TIMEOUT_SEC.toLong(), TimeUnit.SECONDS)
    builder = builder.readTimeout(READ_TIMEOUT_SEC.toLong(), TimeUnit.SECONDS)
    return builder.build()
  }

  @Provides
  @Singleton
  fun provideGson(): Gson {
    return GsonUtil.getInstance().gson
  }

  @Provides
  @Singleton
  fun providesService(
    okHttpClient: OkHttpClient,
    gson: Gson,
    exceptionMapper: IAPIExceptionMapper,
  ): IService {
    val retrofit = Retrofit.Builder()
      .baseUrl(BuildConfig.BASE_SERVER_URL) // Need to be changed once it is working
      .client(okHttpClient)
      .addConverterFactory(ScalarsConverterFactory.create())
      .addConverterFactory(GsonConverterFactory.create(gson))
      .addCallAdapterFactory(RxErrorHandlingCallAdapterFactory.create(exceptionMapper))
      .build()

    return retrofit.create(IService::class.java)
  }

  companion object {
    const val CONNECTION_TIMEOUT_SEC = 90
    const val WRITE_TIMEOUT_SEC = 120
    const val READ_TIMEOUT_SEC = 90
  }

}
