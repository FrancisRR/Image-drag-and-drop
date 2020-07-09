package com.francis.housedesigntablet

import android.os.Bundle
import android.view.*
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.francis.housedesigntablet.utils.UiUtils
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity(), View.OnDragListener {

    private val TAG by lazy { MainActivity::class.java.simpleName }

    private var adapter: RecycleAdapterContainer? = null
    private var innerAdapter: RecycleAdapterView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setUi()
    }

    private fun setUi() {
        setInstance()
        setAction()

    }

    private fun setInstance() {
        createAdapter()
    }

    private fun createAdapter() {
        adapter = RecycleAdapterContainer(this)
        recycler.layoutManager = LinearLayoutManager(this)
        recycler.adapter = adapter

        innerAdapter = RecycleAdapterView()
        recyclerInnerItem.layoutManager = LinearLayoutManager(this)
        recyclerInnerItem.adapter = innerAdapter
    }

    private fun setAction() {
        val list = mutableListOf<ShapeModel>()
        list.add(ShapeModel(1, 0, 0))
        adapter?.setAdapterList(list)

        val viewsList = mutableListOf<ShapeModel>()
        viewsList.add(ShapeModel(2, 0, 0))
        innerAdapter?.setAdapterList(viewsList)


        relContainer.setOnDragListener(this)
    }

    override fun onDrag(parentView: View?, event: DragEvent?): Boolean {
        when (event?.action) {
            DragEvent.ACTION_DRAG_ENTERED ->
                UiUtils.appErrorLog(TAG, "Entered")
            DragEvent.ACTION_DRAG_STARTED ->
                UiUtils.appErrorLog(TAG, "Started")
            DragEvent.ACTION_DRAG_ENDED ->
                UiUtils.appErrorLog(TAG, "Ended")
            DragEvent.ACTION_DRAG_EXITED ->
                UiUtils.appErrorLog(TAG, "Exited")
            DragEvent.ACTION_DRAG_LOCATION ->
                UiUtils.appErrorLog(TAG, "Drag Location")
            DragEvent.ACTION_DROP -> {
                val dropX = event.x
                val dropY = event.y

                val state: ShapeModel = event.localState as ShapeModel

                if (state.id == 1) {
                    val childView: RelativeLayout =
                        LayoutInflater.from(this@MainActivity).inflate(
                            R.layout.item_shape, parentView as RelativeLayout, false
                        ) as RelativeLayout
                    childView.setX(dropX - state.width as Int / 2)
                    childView.setY(dropY - state.height as Int / 2)
                    childView.getLayoutParams().width = state.width
                    childView.getLayoutParams().height = state.height
                    (parentView as RelativeLayout).addView(childView)
                    childView.setOnDragListener(this@MainActivity)
                    childView.setOnTouchListener(View.OnTouchListener { v, event ->
                        onTouchOne(v, event)
                        return@OnTouchListener true
                    })

                } else if (state.id == 3) {

                    val oldView = (event.localState as ShapeModel).view

                    val owner = oldView?.parent as ViewGroup
                    owner.removeView(oldView)

                    val childView: RelativeLayout =
                        LayoutInflater.from(this@MainActivity).inflate(
                            R.layout.item_shape_views, parentView as RelativeLayout, false
                        ) as RelativeLayout
                    childView.setX(dropX - state.width as Int / 2)
                    childView.setY(dropY - state.height as Int / 2)
                    childView.getLayoutParams().width = state.width
                    childView.getLayoutParams().height = state.height
                    (parentView as RelativeLayout).addView(childView)
                    childView.setOnTouchListener(View.OnTouchListener { v, event ->
                        onTouchTwo(v, event)
                        return@OnTouchListener true
                    })
                } else {
                    val childView: RelativeLayout =
                        LayoutInflater.from(this@MainActivity).inflate(
                            R.layout.item_shape_views, parentView as RelativeLayout, false
                        ) as RelativeLayout
                    childView.setX(dropX - state.width as Int / 2)
                    childView.setY(dropY - state.height as Int / 2)
                    childView.getLayoutParams().width = state.width
                    childView.getLayoutParams().height = state.height
                    (parentView as RelativeLayout).addView(childView)
                    childView.setOnTouchListener(View.OnTouchListener { v, event ->
                        onTouchTwo(v, event)
                        return@OnTouchListener true
                    })
                }

                UiUtils.appErrorLog(TAG, "Drop")
            }


        }
        return true
    }


    private var xDelta = 0
    private var yDelta = 0

    fun onTouchOne(v: View?, event: MotionEvent?) {
        touchMoveCommonAction(v, event)
    }

    fun onTouchTwo(v: View?, event: MotionEvent?) {
        val model: ShapeModel = ShapeModel(3)
        model.width = v?.width!!
        model.height = v.height
        model.view = v
        val dragShadowBuilder = View.DragShadowBuilder(v)
        ViewCompat.startDragAndDrop(v, null, dragShadowBuilder, model, 0)
        touchMoveCommonAction(v, event)
    }


    private fun touchMoveCommonAction(v: View?, event: MotionEvent?) {
        val x = event!!.rawX.toInt()
        val y = event.rawY.toInt()

        when (event.action and MotionEvent.ACTION_MASK) {
            MotionEvent.ACTION_DOWN -> {
                val lParams = v!!.getLayoutParams() as (RelativeLayout.LayoutParams)
                xDelta = x - lParams.leftMargin
                yDelta = y - lParams.topMargin
            }
            MotionEvent.ACTION_UP -> Toast.makeText(
                this@MainActivity,
                "I'm here!", Toast.LENGTH_SHORT
            )
                .show()
            MotionEvent.ACTION_MOVE -> {
                val layoutParams = v!!.layoutParams as (RelativeLayout.LayoutParams)
                layoutParams.leftMargin = x - xDelta
                layoutParams.topMargin = y - yDelta
                layoutParams.rightMargin = 0
                layoutParams.bottomMargin = 0
                v.setLayoutParams(layoutParams)
            }
        }
    }


}