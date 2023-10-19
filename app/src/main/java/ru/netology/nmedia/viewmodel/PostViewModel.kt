package ru.netology.nmedia.viewmodel

import androidx.lifecycle.ViewModel
import ru.netology.nmedia.repository.PostRepository
import ru.netology.nmedia.repository.PostRepositoryInMemoryImpl

class PostVewModel: ViewModel() {

    private val repository: PostRepository = PostRepositoryInMemoryImpl()

    val data = repository.get()

    fun like (id: Long) = repository.like(id)

    fun share (id: Long) = repository.share(id)
}