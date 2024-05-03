package com.example.bookapp.model

import androidx.databinding.ObservableField

data class UIBook(val book: Book?) {
    val id = ObservableField<String?>(book?.id)
    val title = ObservableField<String?>(book?.title)
    val author = ObservableField<String?>(book?.author)
    val publishedDate = ObservableField<String?>(book?.publishedDate)
    val isbn = ObservableField<String?>(book?.isbn)

    val hasUpdate: Boolean
        get() = title.get() != book?.title || author.get() != book?.author
                || publishedDate.get() != book?.publishedDate || isbn.get() != book?.isbn

    val invalid: Boolean
        get() = title.get().isNullOrBlank() || author.get().isNullOrBlank()
                || publishedDate.get().isNullOrBlank() || isbn.get().isNullOrBlank()

    fun convertToBook(): Book {
        return Book(
            id = id.get(),
            title = title.get(),
            author = author.get(),
            publishedDate = publishedDate.get(),
            isbn = isbn.get()
        )
    }
}