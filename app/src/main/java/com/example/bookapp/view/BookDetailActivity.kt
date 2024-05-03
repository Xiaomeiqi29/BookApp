package com.example.bookapp.view

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.bookapp.R
import com.example.bookapp.databinding.ActivityBookDetailBinding
import com.example.bookapp.vm.BookDetailViewModel
import com.example.bookapp.vm.BookDetailViewModel.NetworkStatus
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BookDetailActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_KEY_BOOK = "extra_key_book"
    }

    private lateinit var binding: ActivityBookDetailBinding
    private val vm: BookDetailViewModel by viewModels()

    private val textWatcher by lazy {
        object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                vm.updateButtonState()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_book_detail)
        binding.vm = vm
        binding.lifecycleOwner = this

        initViews()
        initObservers()
    }

    override fun onDestroy() {
        super.onDestroy()
        listOf(binding.title, binding.author, binding.publishedDate, binding.isbn).forEach {
            it.removeTextChangedListener(textWatcher)
        }
    }

    private fun initViews() {
        binding.back.setOnClickListener {
            onBackPressed()
        }
        binding.deleteBook.setOnClickListener {
            vm.deleteBook()
        }
        binding.saveBook.setOnClickListener {
            vm.saveBook()
        }
        listOf(binding.title, binding.author, binding.publishedDate, binding.isbn).forEach {
            it.addTextChangedListener(textWatcher)
        }
    }

    private fun initObservers() {
        vm.networkStatus.observe(this) {
            when (it) {
                is NetworkStatus.Success -> {
                    setResult(RESULT_OK)
                    finish()
                }

                is NetworkStatus.Error -> {
                    Toast.makeText(this, it.message.orEmpty(), Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}