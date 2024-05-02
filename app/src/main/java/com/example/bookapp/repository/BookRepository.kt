package com.example.bookapp.repository

import com.example.bookapp.model.Book

interface BookRepository {
    suspend fun getAllBooks(): List<Book>?

    suspend fun addBook(book: Book): Book?

    suspend fun getBookById(id: String): Book?

    suspend fun deleteBook(id: String)

    suspend fun updateBook(book: Book): Book?
}