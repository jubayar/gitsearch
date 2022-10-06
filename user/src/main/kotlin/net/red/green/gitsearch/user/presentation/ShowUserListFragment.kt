package net.red.green.gitsearch.user.presentation

import android.view.LayoutInflater
import android.widget.Toast
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.launch
import net.red.green.core.view.BaseFragment
import net.red.green.gitsearch.user.databinding.FragmentShowUserListBinding

class ShowUserListFragment : BaseFragment<FragmentShowUserListBinding, ShowUserListViewModel>() {
    private val accountAdapter = UserAccountListAdapter()

    override fun constructViewBinding(inflater: LayoutInflater) =
        FragmentShowUserListBinding.inflate(inflater)

    override fun initViewModel(): ShowUserListViewModel =
        ViewModelProvider(this)[ShowUserListViewModel::class.java]


    override fun initOnCreateView() {
        bindingView.listUserAccount.adapter = accountAdapter

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { state ->
                    when(state) {
                        is ShowUserListViewModel.AccountListUiState.Loading -> {}

                        is ShowUserListViewModel.AccountListUiState.Success -> {
                            accountAdapter.setData(state.accounts)
                        }

                        is ShowUserListViewModel.AccountListUiState.Error -> {
                            Toast.makeText(context, state.errorRes.errorMsgList[0], Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        }

        bindingView.listUserAccount.addOnScrollListener(object : RecyclerView.OnScrollListener() {
        })

        viewModel.fetchUserAccountList("Juba", 0)
    }
}
