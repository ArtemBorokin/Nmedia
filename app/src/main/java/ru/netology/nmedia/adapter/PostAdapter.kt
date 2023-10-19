package ru.netology.nmedia.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.netology.nmedia.R
import ru.netology.nmedia.databinding.CardPostBinding
import ru.netology.nmedia.dto.Post
import ru.netology.nmedia.dto.count

typealias LikeListener = (Post) -> Unit
typealias ShareListener = (Post) -> Unit

class PostAdapter (private val listener: LikeListener, private val listener1: ShareListener) : RecyclerView.Adapter<PostViewHolder>() {

    var list : List<Post> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
       val view = CardPostBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PostViewHolder(view, listener, listener1)
    }

    override fun getItemCount() = list.size

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        val post= list[position]
        holder.bind(post)
    }
}
class PostViewHolder (private  val binding: CardPostBinding, private val listener: LikeListener, private val listener1: ShareListener) : RecyclerView.ViewHolder(binding.root) {

    fun bind (post : Post){
        with(binding) {
            author.text = post.author
            published.text = post.published
            content.text = post.content
            countLike.text = count(post.countLikes)
            countShare.text = count(post.countShare)
            countViews.text = count(post.countViews)
            like.setImageResource(
                if (post.likedByMe) (R.drawable.ic_liked_24)
                else (R.drawable.icon_like)
            )

            like.setOnClickListener {
                listener(post)
            }

            share.setOnClickListener {
                listener1(post)
            }
        }
    }
}