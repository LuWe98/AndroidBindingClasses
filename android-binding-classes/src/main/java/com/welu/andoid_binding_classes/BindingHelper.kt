package com.welu.andoid_binding_classes

import android.app.Dialog
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.welu.andoid_binding_classes.fragments.BindingBottomSheetDialogFragment
import com.welu.andoid_binding_classes.fragments.BindingDialog
import com.welu.andoid_binding_classes.fragments.BindingDialogFragment
import com.welu.andoid_binding_classes.fragments.BindingFragment
import com.welu.andoid_binding_classes.recyclerview.BindingListAdapter
import com.welu.andoid_binding_classes.recyclerview.BindingPagingDataAdapter
import java.lang.reflect.ParameterizedType
import kotlin.reflect.KClass

@Suppress("UNCHECKED_CAST")
object BindingHelper {

    private const val INFLATE_METHOD = "inflate"

    private fun <VB : ViewBinding> findGenericBindingTypeWith(classInstance: Any, genericClassToFind: Class<VB>, relativePosition: Int): Class<VB>? = try {
        (classInstance.javaClass.genericSuperclass as ParameterizedType).actualTypeArguments
            .mapNotNull {
                try {
                    it as Class<*>
                } catch (e: Exception) {
                    null
                }
            }.filter {
                genericClassToFind.isAssignableFrom(it)
            }[relativePosition] as Class<VB>
    } catch (e: Exception) {
        null
    }

    private fun <VB : ViewBinding> getClassWith(classInstance: Any, bindingClass: KClass<VB>?, relativePosition: Int) = bindingClass?.java
        ?: findGenericBindingTypeWith(classInstance, ViewBinding::class.java, relativePosition)
        ?: throw IllegalArgumentException("Could not find generic binding class of Instance '$classInstance'. Try adding a fallback class in the constructor.")

    private fun <VB : ViewBinding> findBindingWith(
        fragmentInstance: Fragment,
        bindingClass: KClass<VB>?,
        relativePosition: Int
    ): VB = getClassWith(fragmentInstance, bindingClass, relativePosition)
        .getMethod(INFLATE_METHOD, LayoutInflater::class.java)
        .invoke(null, fragmentInstance.layoutInflater) as VB

    private fun <VB : ViewBinding> findBindingWith(
        dialogInstance: Dialog,
        bindingClass: KClass<VB>?,
        relativePosition: Int
    ): VB = getClassWith(dialogInstance, bindingClass, relativePosition)
        .getMethod(INFLATE_METHOD, LayoutInflater::class.java)
        .invoke(null, dialogInstance.layoutInflater) as VB

    private fun <VB : ViewBinding> findBindingWith(
        adapterInstance: RecyclerView.Adapter<*>,
        bindingClass: KClass<VB>?,
        parent: ViewGroup,
        relativePosition: Int = 0
    ): VB = getClassWith(adapterInstance, bindingClass, relativePosition)
        .getMethod(INFLATE_METHOD, LayoutInflater::class.java, ViewGroup::class.java, Boolean::class.java)
        .invoke(null, LayoutInflater.from(parent.context), parent, false) as VB


    fun <VB : ViewBinding> getBinding(
        adapterInstance: BindingListAdapter<*, VB>,
        bindingClass: KClass<VB>?,
        parent: ViewGroup,
        relativePosition: Int = 0
    ): VB = findBindingWith(adapterInstance, bindingClass, parent, relativePosition)

    fun <VB : ViewBinding> getBinding(
        adapterInstance: BindingPagingDataAdapter<*, VB>,
        bindingClass: KClass<VB>?,
        parent: ViewGroup,
        relativePosition: Int = 0
    ): VB = findBindingWith(adapterInstance, bindingClass, parent, relativePosition)

    fun <VB : ViewBinding> getBinding(fragment: BindingFragment<VB>, bindingClass: KClass<VB>?, relativePosition: Int = 0) =
        findBindingWith(fragment, bindingClass, relativePosition)

    fun <VB : ViewBinding> getBinding(fragment: BindingDialogFragment<VB>, bindingClass: KClass<VB>?, relativePosition: Int = 0) =
        findBindingWith(fragment, bindingClass, relativePosition)

    fun <VB : ViewBinding> getBinding(dialog: BindingDialog<VB>, bindingClass: KClass<VB>?, relativePosition: Int = 0) =
        findBindingWith(dialog, bindingClass, relativePosition)

    fun <VB : ViewBinding> getBinding(fragment: BindingBottomSheetDialogFragment<VB>, bindingClass: KClass<VB>?, relativePosition: Int = 0) =
        findBindingWith(fragment, bindingClass, relativePosition)

}