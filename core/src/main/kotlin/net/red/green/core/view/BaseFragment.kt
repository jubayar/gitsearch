package net.red.green.core.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import net.red.green.core.viewmodel.BaseViewModel

abstract class BaseFragment<VB : ViewDataBinding, T: BaseViewModel> : Fragment()  {
    protected val bindingView: VB
        get() = viewDataBinding
    private lateinit var viewDataBinding: VB

    protected lateinit var viewModel: T

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewDataBinding = DataBindingUtil.inflate(inflater, layoutId(), container, false)
        viewModel = initViewModel()

        initOnCreateView()

        viewModel.showMessage.observe(viewLifecycleOwner) { msg: String ->
            Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
        }

        return viewDataBinding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewDataBinding.unbind()
    }

    @LayoutRes
    protected abstract fun layoutId(): Int

    protected abstract fun initViewModel(): T

    protected abstract fun initOnCreateView()
}
