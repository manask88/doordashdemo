package com.example.doordashinterview.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.HORIZONTAL
import androidx.recyclerview.widget.RecyclerView.VERTICAL
import com.example.doordashinterview.databinding.ActivityMainBinding

class ListRestaurantsActivity : AppCompatActivity() {
    private val viewModel: ListRestaurantsViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val restaurantListAdapter = RestaurantListAdapter()
        val dividerItemDecoration = DividerItemDecoration(this, VERTICAL)

        val linearLayoutManager = LinearLayoutManager(this@ListRestaurantsActivity)
        binding.recyclerView.apply {
            adapter = restaurantListAdapter
            layoutManager = linearLayoutManager
            addItemDecoration(dividerItemDecoration)
            addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    val totalItemCount = linearLayoutManager.itemCount
                    val lastVisibleItemPosition = linearLayoutManager.findLastVisibleItemPosition()

                    viewModel.loadMore(lastVisibleItemPosition, totalItemCount)
                }
            })
        }

        viewModel.liveData.observe(this, Observer {
            restaurantListAdapter.addList(it)
        })

        viewModel.loadInitial()
    }
}
