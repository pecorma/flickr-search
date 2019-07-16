package com.mjpecora.application.flickrsearch.adapters

import android.view.View
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.mjpecora.application.flickrsearch.R
import com.mjpecora.application.flickrsearch.model.Photo
import com.mjpecora.application.flickrsearch.model.State
import kotlinx.android.synthetic.main.item_loading.view.*
import kotlinx.android.synthetic.main.item_photo.view.*

class PhotosAdapter(private val retry: () -> Unit) : PagedListAdapter<Photo, RecyclerView.ViewHolder>(
        object : DiffUtil.ItemCallback<Photo>() {
            override fun areContentsTheSame(oldItem: Photo, newItem: Photo): Boolean {
                return oldItem == newItem
            }

            override fun areItemsTheSame(oldItem: Photo, newItem: Photo): Boolean {
                return oldItem.id == newItem.id
            }
        }
) {

    companion object {
        private const val LOADING = 0
        private const val ITEM = 1
    }

    private var state = State.LOADING

    override fun getItemCount(): Int = super.getItemCount() + if (hasLoading()) 1 else 0

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (getItemViewType(position) == ITEM)
            getItem(position)?.let { (holder as PhotosViewHolder).bindView(it) }
        else (holder as LoadingViewHolder).bindView(state)
    }

    override fun getItemViewType(position: Int): Int {
        return when (position < super.getItemCount()) {
            false -> LOADING
            else -> ITEM
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            ITEM -> PhotosViewHolder(View.inflate(parent.context, R.layout.item_photo, null))
            else -> LoadingViewHolder(View.inflate(parent.context, R.layout.item_loading, null))
        }
    }

    fun setState(state: State) {
        this.state = state
        notifyItemChanged(super.getItemCount())
    }

    private fun hasLoading(): Boolean =
        super.getItemCount() != 0 && (state == State.LOADING || state == State.ERROR)

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

    inner class LoadingViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindView(state: State) {
            when (state) {
                State.ERROR -> itemView.error_lottie?.run {
                    visibility = View.VISIBLE
                    setOnClickListener { retry.invoke() }
                }
                else-> itemView.loading_lottie?.visibility = View.VISIBLE
            }
        }
    }

}