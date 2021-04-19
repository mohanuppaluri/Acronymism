package com.coding.acronymism.ui.fragment.searchfragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.coding.acronymism.R
import com.coding.acronymism.databinding.FragmentSearchBinding
import com.coding.acronymism.room.repository.AcronymRepository
import com.coding.acronymism.ui.activity.MainActivity
import com.coding.acronymism.ui.fragment.meaningfragment.MeaningFragment
import com.coding.acronymism.utils.loadFragment

class SearchFragment : Fragment() {

    private lateinit var viewModel: SearchViewModel
    private lateinit var binding: FragmentSearchBinding
    private lateinit var repository: AcronymRepository

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        (requireActivity() as MainActivity).supportActionBar?.setDisplayHomeAsUpEnabled(false)
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_search, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initBindings()
        initObservers()
        initListeners()
    }

    private fun initBindings() {
        repository = AcronymRepository(requireActivity().application)
        viewModel = ViewModelProvider(requireActivity(), SearchViewModelFactory(repository)).get(
            SearchViewModel::class.java
        )
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
    }

    private fun initObservers() {
        viewModel.error.observe(viewLifecycleOwner, Observer {
            showDialog(
                title = getString(R.string.error_title),
                message = getString(R.string.error_string)
            )
        })

        viewModel.result.observe(viewLifecycleOwner, Observer {
            if (it.isNotEmpty()) {
                requireActivity().loadFragment(
                    R.id.frameLayoutContainer,
                    MeaningFragment.newIntent(it.first())
                )
            } else {
                showDialog(
                    title = getString(R.string.no_results_found_title),
                    message = getString(R.string.no_results_found)
                )
            }
        })

        viewModel.newAcronymId.observe(viewLifecycleOwner, Observer {
            // no-op
        })
    }

    private fun initListeners() {
        binding.buttonSearch.setOnClickListener {
            if (viewModel.validateSearchString(binding.editTextSearch.text.toString())) {
                viewModel.searchAcronym(
                    binding.editTextSearch.text.toString(),
                    if (binding.radioShortForm.isChecked) {
                        SearchViewModel.SearchMode.SHORT_FORM
                    } else {
                        SearchViewModel.SearchMode.FULL_FORM
                    }
                )
            } else {
                showDialog(
                    title = getString(R.string.alert),
                    message = getString(R.string.invalid_search)
                )
            }
        }
    }

    private fun showDialog(title: String, message: String) {
        AlertDialog.Builder(requireContext())
            .setTitle(title)
            .setMessage(message)
            .setPositiveButton(
                getString(R.string.ok)
            ) { dialog, _ -> dialog.dismiss() }
            .show()
    }

    companion object {
        fun newIntent() = SearchFragment()
    }
}
