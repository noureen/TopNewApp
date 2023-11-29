package com.example.topnewapp.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.RequestManager
import com.example.topnewapp.databinding.FragmentHeadlineDetailBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class HeadlineDetailFragment : Fragment() {

    @Inject
    lateinit var glide: RequestManager
    private var _binding: FragmentHeadlineDetailBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHeadlineDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Hide the back button in the ActionBar
        (requireActivity() as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
        getAndDisplayArgsData()
    }

    // Retrieve arguments using SafeArgs and display in UI
    private fun getAndDisplayArgsData() {
        val args: HeadlineDetailFragmentArgs by navArgs()
        val title = args.headlineTitle
        val description = args.headlineDescription
        val imageUrl = args.headlineImgUrl
        val content = args.headlineContent

        binding.titleTextView.text = title
        binding.descriptionTextView.text = description
        binding.contentTextView.text = content
        // Load and display image (using Glide or Picasso)
        glide
            .load(imageUrl)
            .into(binding.headlineImageView)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}