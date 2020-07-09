package com.francis.housedesigntablet

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.RecyclerView

class RecycleAdapterView : RecyclerView.Adapter<RecycleAdapterView.RecycleHolderView>() {

    private val TAG by lazy { RecycleAdapterView::class.java.simpleName }
    private var itemList = mutableListOf<ShapeModel>()

    internal fun setAdapterList(list: MutableList<ShapeModel>) {
        if (list == null) {
            return
        }
        itemList.clear()
        itemList.addAll(list)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecycleHolderView {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_shape_views, parent, false)
        return RecycleHolderView(view)
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    override fun onBindViewHolder(holder: RecycleHolderView, position: Int) {
    }

    inner class RecycleHolderView(view: View) : RecyclerView.ViewHolder(view) {
        var ivShape: RelativeLayout = view.findViewById(R.id.layoutView) as RelativeLayout

        init {
            ivShape.setOnLongClickListener { view: View? ->

                val model: ShapeModel = itemList.get(adapterPosition)
                model.width = view?.width!!
                model.height = view.height
                model.view = view
                val dragShadowBuilder = View.DragShadowBuilder(view)
                ViewCompat.startDragAndDrop(view, null, dragShadowBuilder, model, 0)

                return@setOnLongClickListener true
            }
        }

    }
}