package net.red.green.gitsearch.user.presentation

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.CircleCropTransformation
import net.red.green.gitsearch.user.databinding.ItemUserAccountBinding
import net.red.green.gitsearch.user.presentation.model.UserAccountViewData

sealed class UserAccountListViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val binding = ItemUserAccountBinding.bind(view.rootView)

    abstract fun bindItem(data: UserAccountViewData)
}

class GitUsersListItemViewHolder(view: View) : UserAccountListViewHolder(view) {
    override fun bindItem(data: UserAccountViewData) {
        binding.image.load(data.avatar_url) {
            size(200)
            crossfade(true)
            transformations(CircleCropTransformation())
        }
        binding.name.text = data.login
    }
}
