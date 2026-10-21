package com.tuapp.notekeper.ui.login

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.tuapp.notekeper.R
import com.tuapp.notekeper.databinding.ItemAccountBinding
import com.tuapp.notekeper.model.User
import com.tuapp.notekeper.util.UserAvatars

class UserAccountAdapter(
    private val onClick: (User) -> Unit
) : ListAdapter<User, UserAccountAdapter.AccountViewHolder>(Diff) {

    private var selectedUsername: String = ""

    fun select(username: String) {
        selectedUsername = username
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AccountViewHolder {
        val binding = ItemAccountBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return AccountViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AccountViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class AccountViewHolder(
        private val binding: ItemAccountBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(user: User) {
            val selected = user.username == selectedUsername
            binding.ivAvatar.setImageResource(UserAvatars.forUser(user.id))
            binding.ivAvatar.borderColor = ContextCompat.getColor(
                binding.root.context,
                if (selected) R.color.yellow else R.color.line
            )
            binding.tvUsername.text = user.username
            binding.accountRow.setBackgroundResource(
                if (selected) R.drawable.bg_account_selected else R.drawable.bg_account
            )
            binding.accountRow.setOnClickListener {
                select(user.username)
                onClick(user)
            }
        }
    }

    private object Diff : DiffUtil.ItemCallback<User>() {
        override fun areItemsTheSame(oldItem: User, newItem: User): Boolean = oldItem.id == newItem.id
        override fun areContentsTheSame(oldItem: User, newItem: User): Boolean = oldItem.username == newItem.username
    }
}
