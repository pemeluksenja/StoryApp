package com.pemeluksenja.storyappdicoding.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.pemeluksenja.storyappdicoding.databinding.StoryitemsBinding
import com.pemeluksenja.storyappdicoding.model.ListStoryItem


class StoryAdapter : PagingDataAdapter<ListStoryItem, StoryAdapter.MyViewHolder>(DIFF_CALLBACK) {
    private lateinit var onProfileCallback: OnProfileCallback

    interface OnProfileCallback {
        fun onProfileClicked(data: ListStoryItem)
    }

    fun setOnProfileCallback(onProfileCallback: OnProfileCallback) {
        this.onProfileCallback = onProfileCallback
    }

    class MyViewHolder(private val binding: StoryitemsBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: ListStoryItem) {
            binding.userName.text = data.name
            Glide.with(binding.root.context).load(data.photoUrl).into(binding.userImageStory)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = StoryitemsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val data = getItem(position)
        if (data != null) {
            holder.bind(data)
        }
        holder.itemView.setOnClickListener {
            if (data != null) {
                onProfileCallback.onProfileClicked(data)
            }
        }
        Log.d("OnBindViewHolder: ", data?.name.toString())
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ListStoryItem>() {
            override fun areItemsTheSame(oldItem: ListStoryItem, newItem: ListStoryItem): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: ListStoryItem,
                newItem: ListStoryItem
            ): Boolean {
                return oldItem.id == newItem.id
            }
        }
    }
}