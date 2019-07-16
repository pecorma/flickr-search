package com.mjpecora.application.flickrsearch

import android.app.Activity
import android.graphics.Rect
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mjpecora.application.flickrsearch.adapters.PhotosAdapter
import com.mjpecora.application.flickrsearch.databinding.FragmentPhotosBinding
import com.mjpecora.application.flickrsearch.viewmodels.PhotosViewModel
import kotlinx.android.synthetic.main.layout_search_bar.*

class PhotosFragment : Fragment() {

    private val adapter: PhotosAdapter by lazy { PhotosAdapter{ vm?.retry() } }

    private var vm: PhotosViewModel? = null

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
        binding.photosRv?.run {
            this.adapter = this@PhotosFragment.adapter
            this.layoutManager =  GridLayoutManager(context, 2)
            addItemDecoration(itemDecoration)
        }
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        search_iv?.setOnClickListener {
            if (!search_et?.text.isNullOrEmpty()) {
                (activity?.getSystemService(Activity.INPUT_METHOD_SERVICE) as? InputMethodManager)?.hideSoftInputFromWindow(view?.windowToken, 0)
                initViewModel(search_et.text.toString())
                initState()
                subscribeUi()
            }
        }
    }

    private fun initViewModel(input: String) {
       vm = PhotosViewModel(input)
    }

    private fun initState() {
        vm?.getState()?.observe(this, Observer { state -> adapter.setState(state) })
    }

    private fun subscribeUi() {
        vm?.photosList?.observe(this, Observer { adapter.submitList(it) })
    }

}