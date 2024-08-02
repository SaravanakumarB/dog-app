package com.example.dog_app.page.fav

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.dog_app.R
import com.example.dog_app.data.preference.LocalPreferenceController
import com.example.dog_app.databinding.FragmentFavBreedBinding
import com.example.dog_app.page.breed.BreedListAdapter
import com.example.dog_app.presenter.BasePresenter
import com.example.dog_app.viewmodel.ViewModelProvider
import javax.inject.Inject

class FavBreedFragmentPresenter @Inject constructor(
    private val activity: AppCompatActivity,
    private val fragment: Fragment,
    private val viewModelProvider: ViewModelProvider<FavImageViewModel>,
    private val localPreferenceController: LocalPreferenceController
): BasePresenter() {

    private lateinit var binding: FragmentFavBreedBinding
    private lateinit var viewModel: FavImageViewModel
    private var imageListAdapter: BreedListAdapter?= null

    fun handleOnCreate(inflater: LayoutInflater, container: ViewGroup?
    ): View {
        binding =
            FragmentFavBreedBinding.inflate(inflater, container, /* attachToRoot= */ false)
        viewModel =
            viewModelProvider.getForFragment(fragment, FavImageViewModel::class.java)

        binding.let {
            it.lifecycleOwner = fragment
            it.viewModel = viewModel
        }
        setToolbar()
        setUI()
        return binding.root
    }

    private fun setToolbar() {
        binding.headerLayout.tvTitle.text = activity.getString(R.string.favorite_breeds)
        binding.headerLayout.ivBack.setOnClickListener {
            activity.onBackPressedDispatcher.onBackPressed()
        }
    }

    private fun setUI() {
        imageListAdapter = BreedListAdapter(activity, true, localPreferenceController.getFavoriteBreed())
        binding.rvBreeds.adapter = imageListAdapter
        imageListAdapter?.setBreedList(viewModel.getFavoriteBreeds())
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
}