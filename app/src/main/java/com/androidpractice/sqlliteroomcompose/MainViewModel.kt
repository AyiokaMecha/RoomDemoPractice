package com.androidpractice.sqlliteroomcompose

import android.app.Application
import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.androidpractice.sqlliteroomcompose.database.AppDatabase
import com.androidpractice.sqlliteroomcompose.database.Product



class MainViewModel(context: Context) : ViewModel() {

    val allProducts: LiveData<List<Product>>

    private val repository: DatabaseRepository

    val searchResults: MutableLiveData<List<Product>>

    init {
        val productDb = AppDatabase.getInstance(context)
        val  productDao = productDb.productDao()
        repository = DatabaseRepository(productDao)
        allProducts = repository.allProducts
        searchResults = repository.searchResults
    }

    fun insertProduct(product: Product) {
        repository.insertProduct(product)
    }

    fun findProduct(name: String) {
        repository.findProduct(name)
    }

    fun deleteProduct(name: String) {
        repository.deleteProduct(name)
    }

}