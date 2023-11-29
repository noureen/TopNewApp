package com.example.topnewapp.ui.list

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.example.topnewapp.data.Article
import com.example.topnewapp.databinding.ItemHeadlineBinding


class HeadlineListAdapter(

    private val glide: RequestManager,
    private val onHeadlineClick: (Article) -> Unit,

    ) :
    ListAdapter<Article, HeadlineListAdapter.HeadlineViewHolder>(HeadlineDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HeadlineViewHolder {
        val binding =
            ItemHeadlineBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HeadlineViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HeadlineViewHolder, position: Int) {
        val headline = getItem(position)
        holder.bind(headline)
    }

    inner class HeadlineViewHolder(private val binding: ItemHeadlineBinding) :
        RecyclerView.ViewHolder(binding.root), View.OnClickListener {

        init {
            binding.root.setOnClickListener(this)
        }

        fun bind(article: Article) {
            binding.apply {
                headlineTitleTextView.text = article.title
                descriptionTextView.text = article.description
                glide
                    .load(article.urlToImage)
                    .into(headlineImageView)
            }
        }

        override fun onClick(view: View) {
            val position = adapterPosition
            if (position != RecyclerView.NO_POSITION) {
                val headline = getItem(position)
                onHeadlineClick(headline)
            }
        }
    }
}

class HeadlineDiffCallback : DiffUtil.ItemCallback<Article>() {
    override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
        return oldItem.title == newItem.title
    }

    override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
        return oldItem == newItem
    }
}

