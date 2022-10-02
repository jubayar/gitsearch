package net.red.green.gitsearch.user.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView
import net.red.green.gitsearch.user.R
import net.red.green.gitsearch.user.presentation.model.UserAccountViewData

class UserAccountListAdapter : RecyclerView.Adapter<UserAccountListViewHolder>() {

    private var items = mutableListOf<UserAccountViewData>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserAccountListViewHolder =
        GitUsersListItemViewHolder(inflateView(parent, R.layout.item_user_account))

    private fun inflateView(viewGroup: ViewGroup, @LayoutRes layout: Int) =
        LayoutInflater.from(viewGroup.context).inflate(layout, viewGroup, false)

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holderGit: UserAccountListViewHolder, position: Int) {
        holderGit.bindItem(items[position])
    }

    fun setData(list: List<UserAccountViewData>) {
        this.items.clear()
        this.items.addAll(list)
        notifyDataSetChanged()
    }
}
