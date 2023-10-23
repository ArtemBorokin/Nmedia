package ru.netology.nmedia.dto

data class Post(
    val id: Long,
    val author: String,
    val content: String,
    val published: String,
    val likedByMe: Boolean = false,
    val countViews: Int = 0,
    val countLikes: Int = 0,
    val countShare: Int = 0,
)