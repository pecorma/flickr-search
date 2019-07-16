package com.mjpecora.application.flickrsearch.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.mjpecora.application.flickrsearch.R
import com.mjpecora.application.flickrsearch.model.Photo
import kotlinx.android.synthetic.main.item_photo.view.*

class PhotosAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        private const val LOADING = 0
        private const val ITEM = 1
    }

    private var list: List<Photo>? = null
    private var isLoading: Boolean = true

    override fun getItemCount(): Int = list?.size ?: 1

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        list?.get(position)?.let { (holder as PhotosViewHolder).bindView(it) }
    }

    override fun getItemViewType(position: Int): Int {
        return when (isLoading) {
            true -> LOADING
            else -> ITEM
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            ITEM -> PhotosViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_photo, null))
            else -> LoadingViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_loading, null))
        }
    }

    fun submitList(list: List<Photo>) {
        this.list = list
    }

    inner class PhotosViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindView(item: Photo) {
            itemView.run {
                Glide.with(itemView.context)
                    .load(item.url)
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .into(photo_iv)
                photo_tv?.text = item.title
            }
        }
    }

    inner class LoadingViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

}