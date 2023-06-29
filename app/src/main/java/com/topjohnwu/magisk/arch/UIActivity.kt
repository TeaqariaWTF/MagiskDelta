package com.topjohnwu.magisk.arch

import android.content.res.Resources
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.res.use
import androidx.core.view.WindowCompat
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.google.android.material.snackbar.Snackbar
import com.topjohnwu.magisk.BR
import com.topjohnwu.magisk.core.Config
import com.topjohnwu.magisk.core.base.BaseActivity
import com.topjohnwu.magisk.widget.Pre23CardViewBackgroundColorFixLayoutInflaterListener
import rikka.insets.WindowInsetsHelper
import rikka.layoutinflater.view.LayoutInflaterFactory

abstract class UIActivity<Binding : ViewDataBinding> : BaseActivity(), ViewModelHolder {

    protected lateinit var binding: Binding
    protected abstract val layoutRes: Int

    open val snackbarView get() = binding.root
    open val snackbarAnchorView: View? get() = null

    init {
        AppCompatDelegate.setDefaultNightMode(Config.darkTheme)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        layoutInflater.factory2 = LayoutInflaterFactory(delegate)
            .addOnViewCreatedListener(WindowInsetsHelper.LISTENER)
            .apply {
                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
                    this.addOnViewCreatedListener(Pre23CardViewBackgroundColorFixLayoutInflaterListener.getInstance())
                }
            }

        super.onCreate(savedInstanceState)

        startObserveLiveData()

        // We need to set the window background explicitly since for whatever reason it's not
        // propagated upstream
        obtainStyledAttributes(intArrayOf(android.R.attr.windowBackground))
            .use { it.getDrawable(0) }
            .also { window.setBackgroundDrawable(it) }

        WindowCompat.setDecorFitsSystemWindows(window, false)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            window?.decorView?.post {
                // If navigation bar is short enough (gesture navigation enabled), make it transparent
                if ((window.decorView.rootWindowInsets?.systemWindowInsetBottom
                        ?: 0) < Resources.getSystem().displayMetrics.density * 40) {
                    window.navigationBarColor = Color.TRANSPARENT
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                        window.navigationBarDividerColor = Color.TRANSPARENT
                    }
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                        window.isNavigationBarContrastEnforced = false
                        window.isStatusBarContrastEnforced = false
                    }
                }
            }
        }
    }

    fun setContentView() {
        binding = DataBindingUtil.setContentView<Binding>(this, layoutRes).also {
            it.setVariable(BR.viewModel, viewModel)
            it.lifecycleOwner = this
        }
    }

    fun setAccessibilityDelegate(delegate: View.AccessibilityDelegate?) {
        binding.root.rootView.accessibilityDelegate = delegate
    }

    fun showSnackbar(
        message: CharSequence,
        length: Int = Snackbar.LENGTH_SHORT,
        builder: Snackbar.() -> Unit = {}
    ) = Snackbar.make(snackbarView, message, length)
        .setAnchorView(snackbarAnchorView).apply(builder).show()

    override fun onResume() {
        super.onResume()
        viewModel.let {
            if (it is AsyncLoadViewModel)
                it.startLoading()
        }
    }

    override fun onEventDispatched(event: ViewEvent) = when (event) {
        is ContextExecutor -> event(this)
        is ActivityExecutor -> event(this)
        else -> Unit
    }
}
