package com.francis.housedesigntablet

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.RecyclerView

class RecycleAdapterContainer(val context: Context) : RecyclerView.Adapter<RecycleAdapterContainer.RecycleHolder>() {

    private val TAG by lazy { RecycleAdapterContainer::class.java.simpleName }
    private var itemList = mutableListOf<ShapeModel>()

    internal fun setAdapterList(list: MutableList<ShapeModel>) {
        if (list == null) {
            return
        }
        itemList.clear()
        itemList.addAll(list)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecycleHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_shape, parent, false)
        return RecycleHolder(view)
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    override fun onBindViewHolder(holder: RecycleHolder, position: Int) {
    }

    inner class RecycleHolder(view: View) : RecyclerView.ViewHolder(view) {
        var ivShape: RelativeLayout = view.findViewById(R.id.layoutShape) as RelativeLayout

        init {
            ivShape.setOnLongClickListener { view: View? ->

                val model: ShapeModel = itemList.get(adapterPosition)
                model.width = view?.width!!
                model.height = view.height
                val dragShadowBuilder = View.DragShadowBuilder(view)
                ViewCompat.startDragAndDrop(view, null, dragShadowBuilder, model, 0)

                return@setOnLongClickListener true
            }
        }

    }
}