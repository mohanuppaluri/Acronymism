package com.coding.acronymism.ui.fragment.meaningfragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.coding.acronymism.R
import com.coding.acronymism.adapter.recyclerview.AcronymRecyclerViewAdapter
import com.coding.acronymism.databinding.FragmentMeaningBinding
import com.coding.acronymism.interfaces.OnItemSelectedListener
import com.coding.acronymism.model.AcronymResponse
import com.coding.acronymism.ui.activity.MainActivity
import com.coding.acronymism.utils.hideKeyboard

class MeaningFragment : Fragment(), OnItemSelectedListener {

    private lateinit var viewModel: MeaningViewModel
    private lateinit var binding: FragmentMeaningBinding
    private var acronymRecyclerViewAdapter: AcronymRecyclerViewAdapter? = null
    private lateinit var acronymResponse: AcronymResponse

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (requireActivity() as MainActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
        arguments?.getParcelable<AcronymResponse>(PARAM_RESPONSE)?.let {
            acronymResponse = it
            (requireActivity() as MainActivity).supportActionBar?.setTitle(acronymResponse.sf)
        } ?: run {
            requireActivity().onBackPressed()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_meaning, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        hideKeyboard(requireContext())
        initBinding()
        initUi()
    }

    private fun initBinding() {
        viewModel = ViewModelProvider(requireActivity()).get(MeaningViewModel::class.java)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
    }

    private fun initUi() {
        acronymRecyclerViewAdapter = AcronymRecyclerViewAdapter(this)
        binding.rvMeanings.layoutManager = LinearLayoutManager(requireContext())
        binding.rvMeanings.adapter = acronymRecyclerViewAdapter
        acronymRecyclerViewAdapter?.setData(acronymResponse.lfs ?: emptyList())
    }

    companion object {
        private const val PARAM_RESPONSE = "response"
        fun newIntent(acronymResponse: AcronymResponse) = MeaningFragment().apply {
            arguments = bundleOf(
                PARAM_RESPONSE to acronymResponse
            )
        }
    }

    override fun clickAction(item: Any) {

    }
}