package com.example.dog_app.page.images

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Environment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.dog_app.R
import com.example.dog_app.authhelper.ErrorEnum
import com.example.dog_app.data.preference.LocalPreferenceController
import com.example.dog_app.databinding.FragmentDogImageBinding
import com.example.dog_app.domain.response.GetImagesModel
import com.example.dog_app.errorhandling.RetrofitException
import com.example.dog_app.koin.fragment.FragmentScope
import com.example.dog_app.page.MainActivityApiCall
import com.example.dog_app.presenter.BasePresenter
import com.example.dog_app.utils.Constants
import com.example.dog_app.viewmodel.ViewModelProvider
import io.reactivex.disposables.Disposable
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream
import java.net.URL
import javax.inject.Inject


@FragmentScope
class DogImageFragmentPresenter @Inject constructor(
    private val activity: AppCompatActivity,
    private val fragment: Fragment,
    private val viewModelProvider: ViewModelProvider<DogImageFragmentViewModel>,
    private val localPreferenceController: LocalPreferenceController
) : BasePresenter() {

    @Inject
    lateinit var mainActivityApiCall: MainActivityApiCall

    private lateinit var binding: FragmentDogImageBinding
    private lateinit var viewModel: DogImageFragmentViewModel
    private var imageListAdapter: ImageListAdapter? = null

    fun handleOnCreate(
        inflater: LayoutInflater, container: ViewGroup?, breed: String
    ): View? {
        binding =
            FragmentDogImageBinding.inflate(inflater, container, /* attachToRoot= */ false)

        viewModel =
            viewModelProvider.getForFragment(fragment, DogImageFragmentViewModel::class.java)

        binding.let {
            it.lifecycleOwner = fragment
            it.viewModel = viewModel
        }
        setToolbar(breed)
        setUI()
        mainActivityApiCall.getBreedImageData(breed)
        return binding.root
    }

    private fun setToolbar(breed: String) {
        binding.headerLayout.tvTitle.text = breed
        binding.headerLayout.ivBack.setOnClickListener {
            activity.onBackPressedDispatcher.onBackPressed()
        }
        binding.headerLayout.ivFavouriteIcon.setOnClickListener {
            fragment.findNavController().navigate(
                R.id.action_dogImagesFragment_to_favImageFragment,
            )
        }
    }

    private fun setUI() {
        imageListAdapter =
            ImageListAdapter(activity, false, localPreferenceController.getFavoriteDog()).apply {
                setOnItemClickedListener { image, type, position, _ ->
                    if (type == "FAVORITE") {
                        viewModel.addToFavorite(image)
                        imageListAdapter?.notifyItemChanged(position)
                    } else if (type == "DOWNLOAD") {
                        if(isFileAlreadyDownload(getFileName(image))) {
                            Toast.makeText(activity, "File already downloaded", Toast.LENGTH_SHORT).show()
                        } else {
                            downloadImage(image)
                        }
                    } else if(type == "SHARE") {
                        if(isFileAlreadyDownload(getFileName(image))) {
                            shareFile(getFileName(image))
                        } else {
                            downloadImage(image, true)
                        }
                    }
                }
            }
        binding.rvImages.adapter = imageListAdapter
    }

    private fun shareFile(fileName: String) {
        val filePath = File(
            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS),
            fileName
        )

        val intent = Intent(Intent.ACTION_SEND)
        intent.type = "image/png"
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.putExtra(Intent.EXTRA_TEXT,"Powered by DOG API app");
        intent.putExtra(Intent.EXTRA_STREAM, FileProvider.getUriForFile(activity, activity.getApplicationContext().getPackageName() + ".provider", filePath))
        activity.startActivity(Intent.createChooser(intent, "Share Image"))
    }

    private fun downloadImage(image: String, isShare: Boolean = false) {
        val scope = CoroutineScope(Dispatchers.Default)

        // Launch a new coroutine in the scope
        scope.launch {
            val url = URL(image)
            val inputStream = url.openStream()
            val bitmap = BitmapFactory.decodeStream(inputStream)
            saveImage(getFileName(image), bitmap, isShare)
        }
    }

    private fun isFileAlreadyDownload(fileName: String) : Boolean {
        val filePath = File(
            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS),
            fileName
        )
        if(filePath.exists()) {
            return true
        }
        return false
    }

    private fun getFileName(image: String): String {
        val n = image.lastIndexOf('/')
        return image.substring(n + 1)
    }

    private fun saveImage(fileName: String, bitmap: Bitmap, isShare: Boolean) {
        val filePath = File(
            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS),
            fileName
        )
        val outputStream: OutputStream = FileOutputStream(filePath)
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
        outputStream.flush()
        outputStream.close()
        if(isShare) {
            shareFile(fileName)
        } else {
            activity.runOnUiThread(Runnable {
                Toast.makeText(
                    fragment.requireActivity(), "Sucessfully saved in Download Folder",
                    Toast.LENGTH_SHORT
                ).show()
            })
        }
    }

    fun handleStartLoadingView() {
        viewModel.showLoadingView.postValue(true)
    }

    fun handleStopLoadingView() {
        viewModel.showLoadingView.postValue(false)
    }

    fun handleOnDestroyView() {
        unsubscribe()
    }

    override fun unsubscribe() {
        clearAllCalls()
        viewModel.clearAllCalls()
    }

    override fun logout() {

    }

    fun handleAPISuccess(resultType: String, result: Any?) {
        when (resultType) {
            MainActivityApiCall.GET_BREED_IMAGES_DATA -> {
                viewModel.showLoadingView.postValue(false)
                val list = (result as GetImagesModel).message
                imageListAdapter?.setBreedList(list)
            }
        }
    }

    fun handleAddRxCall(d: Disposable) {
        viewModel.addRxCall(d)
    }

    fun handleAPIFailure(
        errorEnum: ErrorEnum, error: Throwable?
    ) {
        viewModel.showLoadingView.postValue(false)
        if (error is RetrofitException) {
            if (error.code == Constants.REFRESH_TOKEN_EXPIRED) {
                logout()
            } else {
                handleError(errorEnum, error)
            }
        } else {
            handleError(errorEnum, error)
        }
    }

    private fun handleError(errorEnum: ErrorEnum, error: Throwable?) {
        when (errorEnum) {
            ErrorEnum.UNEXPECTED_ERROR -> {
                if (error != null) {

                } else {

                }
            }

            else -> {
            }
        }
    }
}