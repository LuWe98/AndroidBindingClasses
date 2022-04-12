package com.welu.andoid_binding_classes.recyclerview

import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.example.quizapp.view.recyclerview.impl.BindingViewHolder
import com.welu.andoid_binding_classes.BindingHelper
import kotlin.reflect.KClass

abstract class BindingPagingDataAdapter<T : Any, VB: ViewBinding>(diffCallback: DiffUtil.ItemCallback<T>, private val bindingClass: KClass<VB>? = null) :
    PagingDataAdapter<T, BindingPagingDataAdapter<T, VB>.BindingPagingDataAdapterViewHolder>(diffCallback) {

    override fun onBindViewHolder(vh: BindingPagingDataAdapterViewHolder, position: Int) {
        getItem(position)?.let { vh.bind(it) }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = BindingPagingDataAdapterViewHolder(BindingHelper.getBinding(this, bindingClass, parent))

    inner class BindingPagingDataAdapterViewHolder(val binding: VB) : BindingViewHolder<T>(binding) {
        init {
            initListeners(binding, this)
        }

        override fun bind(item: T) {
            bindViews(binding, item, adapterPosition)
        }
    }

    fun getItem(viewHolder: RecyclerView.ViewHolder): T? = getItem(viewHolder.adapterPosition)

    abstract fun initListeners(binding: VB, vh: BindingPagingDataAdapterViewHolder)

    abstract fun bindViews(binding: VB, item: T, position: Int)

}