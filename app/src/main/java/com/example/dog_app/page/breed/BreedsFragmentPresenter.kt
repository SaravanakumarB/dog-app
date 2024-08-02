package com.example.dog_app.page.breed

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.dog_app.R
import com.example.dog_app.data.preference.LocalPreferenceController
import com.example.dog_app.databinding.FragmentBreedsBinding
import com.example.dog_app.presenter.BasePresenter
import com.example.dog_app.viewmodel.ViewModelProvider
import javax.inject.Inject

class BreedsFragmentPresenter @Inject constructor(
    private val activity: AppCompatActivity,
    private val fragment: Fragment,
    private val viewModelProvider: ViewModelProvider<BreedsFragmentViewModel>,
    private val localPreferenceController: LocalPreferenceController
): BasePresenter() {

    private lateinit var binding: FragmentBreedsBinding
    private lateinit var viewModel: BreedsFragmentViewModel
    private var breedListAdapter: BreedListAdapter? = null

    fun handleOnCreate(inflater: LayoutInflater, container: ViewGroup?
    ): View? {
        binding =
            FragmentBreedsBinding.inflate(inflater, container, /* attachToRoot= */ false)
        viewModel =
            viewModelProvider.getForFragment(fragment, BreedsFragmentViewModel::class.java)

        binding.let {
            it.lifecycleOwner = fragment
            it.viewModel = viewModel
        }
        setToolbar()
        setUI()
        return binding.root
    }

    private fun setToolbar() {
        binding.headerLayout.tvTitle.text = activity.getString(R.string.breeds)
        binding.headerLayout.ivBack.setOnClickListener {
            activity.finish()
        }
        binding.headerLayout.ivFavouriteIcon.setOnClickListener {
            fragment.findNavController().navigate(
                R.id.action_breedListFragment_to_favBreedFragment,
            )
        }
    }

    private fun setUI() {
        breedListAdapter = BreedListAdapter(activity, false, localPreferenceController.getFavoriteBreed()).apply {
            setOnItemClickedListener { breed, type, position, _ ->
                if(type == "NAME") {
                    fragment.findNavController().navigate(
                        R.id.action_breedListFragment_to_dogImagesFragment,
                        bundleOf("breed" to breed)
                    )
                } else if (type == "FAVORITE") {
                    viewModel.addToFavorite(breed)
                    breedListAdapter?.setFavList(localPreferenceController.getFavoriteBreed())
                    breedListAdapter?.notifyItemChanged(position)
                }
            }
        }
        binding.rvBreeds.adapter = breedListAdapter
        breedListAdapter?.setBreedList(viewModel.getListOfBreeds())
    }

    fun handleOnDestroy() {
        viewModel.unsubscribe()
    }
    override fun unsubscribe() {
        TODO("Not yet implemented")
    }

    override fun logout() {
        TODO("Not yet implemented")
    }
}