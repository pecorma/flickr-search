package com.mjpecora.application.flickrsearch

import android.graphics.Rect
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mjpecora.application.flickrsearch.adapters.PaginationScrollListener
import com.mjpecora.application.flickrsearch.adapters.PhotosAdapter
import com.mjpecora.application.flickrsearch.databinding.FragmentPhotosBinding
import com.mjpecora.application.flickrsearch.viewmodels.PhotoViewModelFactory
import com.mjpecora.application.flickrsearch.viewmodels.PhotosViewModel

class PhotosFragment : Fragment() {

    private var currentPage = 1
    private var isLastPage = false

    private val adapter: PhotosAdapter by lazy { PhotosAdapter() }

    private val vm: PhotosViewModel by viewModels { PhotoViewModelFactory() }

    private val itemDecoration: RecyclerView.ItemDecoration by lazy {
        object : RecyclerView.ItemDecoration() {
            override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
                super.getItemOffsets(outRect, view, parent, state)
                val margin = (resources.displayMetrics.density * 8).toInt()
                with(outRect) {
                    val location = parent.getChildAdapterPosition(view)
                    when  {
                        location == 0 -> {
                            this.left = margin
                            this.top = margin
                            this.right = margin
                            this.bottom = margin
                        }
                        location == 1 -> {
                            this.top = margin
                            this.right = margin
                            this.bottom = margin
                        }
                        location % 2 == 0-> {
                            this.left = margin
                            this.right = margin
                            this.bottom = margin
                        }
                        else -> {
                            this.right = margin
                            this.bottom = margin
                        }
                    }
                }
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding = FragmentPhotosBinding.inflate(inflater, container, false)
        val layoutManager = GridLayoutManager(context, 2)
        binding.swipeRefresh?.setOnRefreshListener {

        }
        binding.photosRv?.run {
            this.layoutManager = layoutManager
            addItemDecoration(itemDecoration)
            this.adapter = adapter
            addOnScrollListener( object : PaginationScrollListener(layoutManager) {
                override fun isLastPage(): Boolean {

                }

                override fun isLoading(): Boolean {
                    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                }

                override fun loadMoreItems() {
                    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                }
            })
        }
        subscribeUi(adapter)
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        //search_et?.text?.toString()?.let { vm.fetchPhotos(it) }
    }

    private fun subscribeUi(adapter: PhotosAdapter) {
        vm.photosLiveData.observe(this, Observer { adapter.submitList(it) })
    }

}