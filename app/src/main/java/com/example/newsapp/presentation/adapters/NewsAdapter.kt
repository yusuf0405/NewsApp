package com.example.newsapp.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.newsapp.R
import com.example.newsapp.databinding.NewsItemBinding
import com.example.newsapp.domain.models.Article
import com.squareup.picasso.Picasso
import kotlinx.coroutines.DelicateCoroutinesApi
import javax.inject.Inject


@DelicateCoroutinesApi
class NewsAdapter @Inject constructor(
    private val actionListener: ItemOnClickListener,
) : PagingDataAdapter<Article, NewsAdapter.NewsViewHolder>(MovieDiffItemCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val layoutInflater =
            LayoutInflater.from(parent.context).inflate(R.layout.news_item, parent, false)
        val binding = NewsItemBinding.bind(layoutInflater)
        return NewsViewHolder(binding = binding)
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }


    inner class NewsViewHolder(private val binding: NewsItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(article: Article) {
            binding.apply {
                title.text = article.title
                Picasso.get()
                    .load(article.urlToImage)
                    .into(image)
            }
            itemView.setOnClickListener {
                actionListener.showNewsToUrl(article.url!!)

            }
        }
    }
}


private object MovieDiffItemCallback : DiffUtil.ItemCallback<Article>() {

    override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
        return oldItem.title == newItem.title && oldItem.title == newItem.title
    }
}