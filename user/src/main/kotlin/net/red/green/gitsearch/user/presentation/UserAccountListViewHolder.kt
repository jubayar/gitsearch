package net.red.green.gitsearch.user.presentation

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import net.red.green.gitsearch.user.databinding.ItemUserAccountBinding
import net.red.green.gitsearch.user.presentation.model.UserAccountViewData

sealed class UserAccountListViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val binding = ItemUserAccountBinding.bind(view.rootView)

    abstract fun bindItem(data: UserAccountViewData)
}

class GitUsersListItemViewHolder(view: View) : UserAccountListViewHolder(view) {
    override fun bindItem(data: UserAccountViewData) {
        //Picasso.get().load(data.avatar_url).resize(200, 0).placeholder(R.mipmap.ic_launcher).into(binding.image)
        binding.name.text = data.login
    }
}
