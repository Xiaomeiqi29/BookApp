package com.example.bookapp.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.bookapp.R
import com.example.bookapp.databinding.ItemBookLayoutBinding
import com.example.bookapp.model.Book

class BookListAdapter(
    private val listener: OnActionListener?
) : RecyclerView.Adapter<BookListAdapter.BookViewHolder>() {
    var books: List<Book> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookViewHolder {
        return BookViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context), R.layout.item_book_layout, parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: BookViewHolder, position: Int) {
        holder.bind(books[position])
    }

    override fun getItemCount(): Int {
        return books.size
    }

    inner class BookViewHolder(
        private val binding: ItemBookLayoutBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.editBook.setOnClickListener {
                listener?.editBook()
            }

            binding.deleteBook.setOnClickListener {
                listener?.deleteBook()
            }
        }

        fun bind(book: Book) {
            binding.book = book
        }
    }
}

interface OnActionListener {
    fun editBook()
    fun deleteBook()
}