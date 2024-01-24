package com.androidpractice.sqlliteroomcompose

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.androidpractice.sqlliteroomcompose.database.Product
import com.androidpractice.sqlliteroomcompose.database.ProductDao
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class DatabaseRepository(val productDao: ProductDao) {

    val allProducts: LiveData<List<Product>> = productDao.getAllProducts()

    val searchResults = MutableLiveData<List<Product>>()

    private val coroutineScope = CoroutineScope(Dispatchers.Main)
    fun insertProduct(newProduct: Product) {
        coroutineScope.launch(Dispatchers.IO) {
            productDao.insertProduct(newProduct)
        }
    }
    fun deleteProduct(name: String) {
        coroutineScope.launch(Dispatchers.IO) {
            productDao.deleteProduct(name)
        }
    }
    fun findProduct(name: String) {
        coroutineScope.launch(Dispatchers.Main) {
            searchResults.value = asyncFind(name).await()
        }
    }
    private fun asyncFind(name: String): Deferred<List<Product>?> {
        return coroutineScope.async(Dispatchers.IO) {
            productDao.findProducts(name)
        }
    }
}