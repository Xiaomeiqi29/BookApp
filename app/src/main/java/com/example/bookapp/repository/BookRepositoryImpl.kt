package com.example.bookapp.repository

import com.example.bookapp.model.Book
import com.example.bookapp.repository.remote.BookApiService
import retrofit2.Response
import javax.inject.Inject

class BookRepositoryImpl @Inject constructor(
    private val bookApiService: BookApiService
) : BookRepository {
    override suspend fun getAllBooks(): List<Book>? {
        return bookApiService.getAllBooks()
    }

    override suspend fun saveBook(book: Book?): Book? {
        return bookApiService.saveBook(book)
    }

    override suspend fun getBookById(id: String): Response<Book>? {
        return bookApiService.getBookById(id)
    }

    override suspend fun deleteBook(id: String) {
        bookApiService.deleteBook(id)
    }
}