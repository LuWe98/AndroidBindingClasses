package com.welu.andoid_binding_classes.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.viewbinding.ViewBinding
import com.welu.andoid_binding_classes.BindingHelper.getBinding
import kotlin.reflect.KClass

abstract class BindingDialogFragment<VB : ViewBinding>(private val bindingClass: KClass<VB>? = null) : DialogFragment() {

    private var _binding: VB? = null
    val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) =
        getBinding(this, bindingClass).also { _binding = it }.root

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}