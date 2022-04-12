package com.welu.andoid_binding_classes.recyclerview

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.example.quizapp.view.recyclerview.impl.BindingViewHolder
import com.welu.andoid_binding_classes.BindingHelper
import kotlin.reflect.KClass

abstract class BindingListAdapter<T : Any, VB : ViewBinding>(
    diffCallback: DiffUtil.ItemCallback<T>,
    private val bindingClass: KClass<VB>? = null,
    private val relativeVbPosition: Int = 0
) : ListAdapter<T, BindingListAdapter<T, VB>.BindingListAdapterViewHolder>(diffCallback) {

    override fun onBindViewHolder(vh: BindingListAdapterViewHolder, position: Int) {
        getItem(position)?.let { vh.bind(it) }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        BindingListAdapterViewHolder(BindingHelper.getBinding(this, bindingClass, parent, relativeVbPosition))

    inner class BindingListAdapterViewHolder(private val binding: VB) : BindingViewHolder<T>(binding) {
        init {
            initListeners(binding, this)
        }

        override fun bind(item: T) {
            bindViews(binding, item, adapterPosition)
        }
    }

    fun getItem(viewHolder: RecyclerView.ViewHolder): T = getItem(viewHolder.adapterPosition)

    abstract fun initListeners(binding: VB, vh: BindingListAdapterViewHolder)

    abstract fun bindViews(binding: VB, item: T, position: Int)

    fun moveItem(fromPosition : Int, toPosition : Int){
        submitList(currentList.toMutableList().apply {
            add(toPosition, removeAt(fromPosition))
        })
    }
}