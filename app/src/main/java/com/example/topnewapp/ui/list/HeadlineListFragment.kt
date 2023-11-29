package com.example.topnewapp.ui.list

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.RequestManager
import com.example.topnewapp.R
import com.example.topnewapp.data.Source
import com.example.topnewapp.databinding.FragmentHeadlineListBinding
import com.example.topnewapp.util.ApiResult
import com.example.topnewapp.util.MarginItemDecoration
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class HeadlineListFragment : Fragment() {

    @Inject
    lateinit var glide: RequestManager

    private var _binding: FragmentHeadlineListBinding? = null
    lateinit var viewModel: HeadlineListViewModel
    private lateinit var adapterHeadline: HeadlineListAdapter
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentHeadlineListBinding.inflate(inflater, container, false)
        val root: View = binding.root

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel =
            ViewModelProvider(this)[HeadlineListViewModel::class.java]

        // Hide the back button in the ActionBar
        (requireActivity() as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(false)

        subscribeObservers()
        setupAdapter()
        setupRecyclerview()

    }

    // Set up adapter for the list of headlines
    private fun setupAdapter() {
        adapterHeadline = HeadlineListAdapter(glide) { selectedHeadline ->
            // Navigate to the detailed view when a headline is clicked
            navigateToDetailHeadline(
                selectedHeadline.title,
                selectedHeadline.description ?: "",
                selectedHeadline.urlToImage ?: "",
                selectedHeadline.content ?: ""
            )
        }
    }

    //Navigate to Detail fragment with title,description,image and content data
    private fun navigateToDetailHeadline(
        title: String?,
        description: String?,
        urlToImage: String?,
        content: String?
    ) {
        findNavController().navigate(
            HeadlineListFragmentDirections.actionNavigationListToNavigationDetail(
                title ?: "",
                description ?: "",
                urlToImage ?: "",
                content ?: ""
            )
        )
    }

    // Set up RecyclerView for the list of headlines
    private fun setupRecyclerview() {
        binding.headlineRecyclerView.apply {
            activity?.let {
                MarginItemDecoration(
                    it,
                    2, 2, 2, 2
                )
            }?.let {
                addItemDecoration(
                    it
                )
            }
            adapter = adapterHeadline
            layoutManager = LinearLayoutManager(requireContext())
        }
    }


    // Observe the top headlines and update the UI
    private fun subscribeObservers() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.topHeadlines.collect { result ->
                when (result) {
                    is ApiResult.Success -> {
                        showLoadingIndicator(false)
                        if (result.data.isEmpty()) {
                            showEmptyState()
                        } else {
                            hideErrorState()
                            setProviderTitle(result.data[0].source)
                            adapterHeadline.submitList(result.data )
                        }
                    }
                    is ApiResult.Error -> {
                        showLoadingIndicator(false)
                        showErrorState(result.exception?.message ?: "Unknown error")
                    }

                    is ApiResult.Loading -> showLoadingIndicator(true)
                }
            }
        }
    }

    private fun setProviderTitle(source: Source?) {
        binding.titleTextView.text = source?.name
    }


    // Handle UI updates for loading state
    private fun showLoadingIndicator(isLoading: Boolean) {
        binding.progressIndicator.visibility = if (isLoading) View.VISIBLE else View.GONE
        binding.txtMsg.visibility = View.GONE
        binding.headlineRecyclerView.visibility = View.VISIBLE
    }


    // Handle UI updates for error state
    private fun showErrorState(errorMessage: String) {
        binding.txtMsg.text = errorMessage
        binding.txtMsg.visibility = View.VISIBLE
        binding.headlineRecyclerView.visibility = View.GONE
    }

    // Handle UI updates for empty state
    private fun showEmptyState() {
        binding.txtMsg.text = resources.getString(R.string.no_headlines_available)
        binding.txtMsg.visibility = View.VISIBLE
        binding.headlineRecyclerView.visibility = View.GONE
    }

    // Hide error state and empty state
    private fun hideErrorState() {
        binding.txtMsg.visibility = View.GONE
        binding.headlineRecyclerView.visibility = View.VISIBLE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
