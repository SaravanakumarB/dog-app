import com.example.dog_app.data.BuildConfig
import com.example.dog_app.data.IService
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test


class IServiceTest {

    @Test
    fun test() {
        assert(true)
    }

    @Test
    fun `formats all breed api url`() {
        val apiUrl = "https://dog.ceo/api/breeds/list/all"
        val formattedUrl = BuildConfig.BASE_SERVER_URL + IService.GET_ALL_BREED_URL

        assertThat(formattedUrl).isEqualTo(apiUrl)
    }

    @Test
    fun `formats get breed images api url`() {
        val apiUrl = BuildConfig.BASE_SERVER_URL + "breed/{breed}/images"
        val formattedUrl = BuildConfig.BASE_SERVER_URL + IService.GET_BREED_IMAGES

        assertThat(formattedUrl).isEqualTo(apiUrl)
    }

    @Test
    fun `formats get random breed image api url`() {
        val apiUrl = BuildConfig.BASE_SERVER_URL + "breeds/image/random"
        val formattedUrl = BuildConfig.BASE_SERVER_URL + IService.GET_RANDOM_BREED_IMAGE

        assertThat(formattedUrl).isEqualTo(apiUrl)
    }
}