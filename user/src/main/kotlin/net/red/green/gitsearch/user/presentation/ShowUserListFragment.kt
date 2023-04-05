package net.red.green.gitsearch.user.presentation

import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.launch
import net.red.green.core.view.BaseFragment
import net.red.green.gitsearch.user.R
import net.red.green.gitsearch.user.databinding.FragmentShowUserListBinding

class ShowUserListFragment : BaseFragment<FragmentShowUserListBinding, ShowUserListViewModel>() {
    private val accountAdapter = UserAccountListAdapter()
    private val showUserListViewModel: ShowUserListViewModel by viewModels { ShowUserListViewModel.Factory }

    override fun constructViewBinding(inflater: LayoutInflater) =
        FragmentShowUserListBinding.inflate(inflater)

    override fun initViewModel(): ShowUserListViewModel = showUserListViewModel

    override fun initOnCreateView() {
        updateToolbar()
        bindingView.listUserAccount.adapter = accountAdapter

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { state ->
                    when (state) {
                        is ShowUserListViewModel.AccountListUiState.Loading -> {
                            bindingView.listUserAccount.visibility =
                                if (state.flag) View.GONE else View.VISIBLE
                            bindingView.emptyPage.visibility =
                                if (!state.flag) View.GONE else View.VISIBLE
                        }

                        is ShowUserListViewModel.AccountListUiState.Success -> {
                            accountAdapter.setData(state.accounts)
                        }

                        is ShowUserListViewModel.AccountListUiState.Error -> {
                            Toast.makeText(
                                context,
                                state.errorRes.errorMsgList[0],
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }
            }
        }

        bindingView.listUserAccount.addOnScrollListener(object : RecyclerView.OnScrollListener() {
        })
    }

    private fun updateToolbar() {
        bindingView.searchUserToolbar.inflateMenu(R.menu.user_search_menu_list)

        val searchItem = bindingView.searchUserToolbar.menu.findItem(R.id.search)
        val searchView = searchItem?.actionView as SearchView

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let { viewModel.fetchUserAccountList(it, 1) }
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })
    }
}
