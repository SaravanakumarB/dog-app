import com.example.dog_app.data.IService
import com.example.dog_app.data.repositories.DogDataRepository
import com.example.dog_app.data.response.GetImagesResponse
import com.example.dog_app.domain.response.GetImagesModel
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import io.reactivex.Single
import org.junit.Before
import org.junit.Test
import org.assertj.core.api.Assertions.assertThat

class DogRepositoryTest {
    private val service: IService = mock()

    private lateinit var repository: DogDataRepository

    @Before
    fun setUp() {
        repository = DogDataRepository(service)
    }

    @Test
    fun `fetches get breed images api response data`() {
        val getImage = GetImagesResponse("success", arrayListOf())
        whenever(service.getBreedImages("hound")).thenReturn(Single.just(getImage))

        val result = repository.getBreedsImages("hound").test()

        verify(service).getBreedImages("hound")
        assertThat(result.values()[0]).isEqualTo(GetImagesModel("success", arrayListOf()))
    }
}
