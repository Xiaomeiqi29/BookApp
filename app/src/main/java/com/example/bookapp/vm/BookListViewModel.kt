package com.example.bookapp.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bookapp.model.Book
import com.example.bookapp.repository.BookRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class BookListViewModel @Inject constructor(
    private val bookRepository: BookRepository
) : ViewModel() {

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    private val _books = MutableLiveData<List<Book>>()
    val books: LiveData<List<Book>> = _books

    fun getAllBooks() {
        viewModelScope.launch {
            try {
                val books = bookRepository.getAllBooks()
                _books.value = books
            } catch (e: Exception) {
                _error.value = e.message
            }
        }
    }
}