package ru.netology.nmedia.dto

data class Post(
    val id: Long,
    val author: String,
    val content: String,
    val published: String,
    var likedByMe: Boolean = false,
    var countViews: Int = 0,
    var countLikes: Int = 0,
    var countShare: Int = 0,
)