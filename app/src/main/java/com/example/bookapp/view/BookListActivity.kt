package com.example.bookapp.view

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.InputFilter.LengthFilter
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.bookapp.R
import com.example.bookapp.databinding.ActivityBookListBinding
import com.example.bookapp.model.Book
import com.example.bookapp.view.BookDetailActivity.Companion.EXTRA_KEY_BOOK
import com.example.bookapp.view.adapter.BookListAdapter
import com.example.bookapp.view.adapter.OnActionListener
import com.example.bookapp.vm.BookListViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class BookListActivity : AppCompatActivity(), OnActionListener {

    private lateinit var binding: ActivityBookListBinding
    private val vm: BookListViewModel by viewModels()
    private val bookListAdapter by lazy { BookListAdapter(this) }
    private val bookDetailActivityLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { activityResult ->
            if (activityResult.resultCode == Activity.RESULT_OK) {
                vm.fetchBookList()
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_book_list)
        binding.vm = vm
        binding.lifecycleOwner = this

        initViews()
        initObservers()
        vm.fetchBookList()
    }

    override fun editBook(book: Book?) {
        goToBookDetailActivity(book)
    }

    private fun initViews() {
        binding.bookList.adapter = bookListAdapter

        binding.addBook.setOnClickListener {
            goToBookDetailActivity()
        }

        binding.iconSearch.setOnClickListener {
            vm.getBookById()
        }
    }

    private fun initObservers() {
        vm.books.observe(this) {
            bookListAdapter.books = it
        }

        vm.searchResult.observe(this) {

        }

        vm.error.observe(this) {
            it?.let {
                Toast.makeText(this, it, Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun goToBookDetailActivity(book: Book? = null) {
        val intent = Intent(this, BookDetailActivity::class.java)
        book?.let {
            intent.putExtra(EXTRA_KEY_BOOK, it)
        }
        bookDetailActivityLauncher.launch(intent)
    }
}