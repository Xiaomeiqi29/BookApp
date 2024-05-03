package com.example.bookapp.repository

import com.example.bookapp.model.Book
import retrofit2.Response

interface BookRepository {
    suspend fun getAllBooks(): List<Book>?

    suspend fun saveBook(book: Book?): Book?

    suspend fun getBookById(id: String): Response<Book>?

    suspend fun deleteBook(id: String)
}