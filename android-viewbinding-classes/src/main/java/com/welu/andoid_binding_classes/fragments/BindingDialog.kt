package com.welu.andoid_binding_classes.fragments

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import androidx.viewbinding.ViewBinding
import com.welu.andoid_binding_classes.BindingHelper.getBinding
import kotlin.reflect.KClass

abstract class BindingDialog<VB : ViewBinding>(context: Context, theme: Int, private val bindingClass: KClass<VB>? = null) : Dialog(context, theme) {

    private var _binding: VB? = null
    val binding get() = _binding!!

    init { initBinding() }

    private fun initBinding () {
        _binding = getBinding(this, bindingClass)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        _binding = null
    }
}