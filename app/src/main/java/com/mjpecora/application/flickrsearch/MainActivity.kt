package com.mjpecora.application.flickrsearch

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.mjpecora.application.flickrsearch.adapters.PhotosAdapter
import com.mjpecora.application.flickrsearch.databinding.ActivityMainBinding
import com.mjpecora.application.flickrsearch.viewmodels.PhotosViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DataBindingUtil.setContentView(this, R.layout.activity_main) as ActivityMainBinding
    }


}
