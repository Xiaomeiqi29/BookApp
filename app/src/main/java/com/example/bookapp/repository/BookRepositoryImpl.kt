package com.example.bookapp.repository

import com.example.bookapp.model.Book
import com.example.bookapp.repository.remote.BookApiService
import javax.inject.Inject

class BookRepositoryImpl @Inject constructor(
    private val bookApiService: BookApiService
) : BookRepository {
    override suspend fun getAllBooks(): List<Book>? {
        return bookApiService.getAllBooks()
    }

    override suspend fun addBook(book: Book): Book? {
        return bookApiService.addBook(book)
    }

    override suspend fun getBookById(id: String): Book? {
        return bookApiService.getBookById(id)
    }

    override suspend fun deleteBook(id: String) {
        bookApiService.deleteBook(id)
    }

    override suspend fun updateBook(book: Book): Book? {
        return bookApiService.updateBook(book)
    }
}