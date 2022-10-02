package net.red.green.gitsearch.user.presentation

import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import net.red.green.core.view.BaseFragment
import net.red.green.gitsearch.user.R
import net.red.green.gitsearch.user.databinding.FragmentShowUserListBinding

class ShowUserListFragment : BaseFragment<FragmentShowUserListBinding, ShowUserListViewModel>() {
    private val accountAdapter = UserAccountListAdapter()

    override fun layoutId(): Int = R.layout.fragment_show_user_list

    override fun initViewModel(): ShowUserListViewModel =
        ViewModelProvider(this)[ShowUserListViewModel::class.java]


    override fun initOnCreateView() {
        bindingView.listUserAccount.adapter = accountAdapter

        bindingView.listUserAccount.addOnScrollListener(object : RecyclerView.OnScrollListener() {
        })

        viewModel.listUserAccount.observe(viewLifecycleOwner) { userAccountList ->
            accountAdapter.setData(userAccountList)
        }

        viewModel.fetchUserAccountList("Juba", 0)
    }
}
