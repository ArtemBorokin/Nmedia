package ru.netology.nmedia.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.netology.nmedia.R
import ru.netology.nmedia.databinding.CardPostBinding
import ru.netology.nmedia.dto.Post

interface OnInteractionListener {
    fun OnLikeListener(post: Post) {}
    fun OnShareListener(post: Post) {}
    fun OnRemoveListener(post: Post) {}
    fun OnEditListener(post: Post) {}
}

class PostAdapter(
    private val onInteractionListener: OnInteractionListener
) : ListAdapter<Post, PostViewHolder>(
    PostViewHolder.PostDiffCallback
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val view = CardPostBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PostViewHolder(view, onInteractionListener)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        val post = getItem(position)
        holder.bind(post)
    }

}

class PostViewHolder(
    private val binding: CardPostBinding,
    private val onInteractionListener: OnInteractionListener
) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(post: Post) {
        with(binding) {
            author.text = post.author
            content.text = post.content
            published.text = post.published
            share.text = count(post.countShare)
            countViews.text = count(post.countViews)
            //countLike.text = count(post.countLikes)

            like.isChecked = post.likedByMe
            like.text = count(post.countLikes)

            like.setOnClickListener {
                onInteractionListener.OnLikeListener(post)
            }

            share.setOnClickListener {
                onInteractionListener.OnShareListener(post)
            }

            menu.setOnClickListener {
                PopupMenu(it.context, it).apply {
                    inflate(R.menu.options_post)
                    setOnMenuItemClickListener { menuItem ->
                        when (menuItem.itemId) {
                            R.id.edit -> {
                                onInteractionListener.OnEditListener(post)
                                true
                            }

                            R.id.remove -> {
                                onInteractionListener.OnRemoveListener(post)
                                true
                            }

                            else -> false
                        }
                    }
                }.show()
            }
        }
    }

    fun count(count: Int): String {
        val hundred = (count % 1000) / 100
        val thousand = count / 1000
        val million = count / 1000000
        val hundredThousand = (count % 1000000) / 100000

        when {
            count < 1000 ->
                return count.toString()

            count in 1000..9999 ->
                return if (count % 1000 in 0..99) {
                    "$thousand K"
                } else return "$thousand.$hundred K"

            count in 10000..999999 ->
                return "$thousand K"

            else -> {
                return if (count % 1000000 in 0..99999) {
                    "$million M"
                } else {
                    "$million.$hundredThousand M"
                }
            }
        }
    }

    object PostDiffCallback : DiffUtil.ItemCallback<Post>() {
        override fun areItemsTheSame(oldItem: Post, newItem: Post) = oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Post, newItem: Post) = oldItem == newItem

    }
}