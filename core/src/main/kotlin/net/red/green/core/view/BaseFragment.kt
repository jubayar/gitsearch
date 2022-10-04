package net.red.green.core.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import net.red.green.core.viewmodel.BaseViewModel

abstract class BaseFragment<VB : ViewBinding, T: BaseViewModel> : Fragment()  {
    protected val bindingView: VB
        get() = viewDataBinding
    private lateinit var viewDataBinding: VB

    protected lateinit var viewModel: T

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewDataBinding = constructViewBinding(inflater)
        viewModel = initViewModel()

        initOnCreateView()

        viewModel.showMessage.observe(viewLifecycleOwner) { msg: String ->
            Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
        }

        return viewDataBinding.root
    }

    protected abstract fun constructViewBinding(inflater: LayoutInflater): VB

    protected abstract fun initViewModel(): T

    protected abstract fun initOnCreateView()
}
