package com.francis.housedesigntablet

import android.os.Bundle
import android.view.DragEvent
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.francis.housedesigntablet.utils.UiUtils
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_test.*


class MainActivity : AppCompatActivity(), View.OnTouchListener {

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
        handleDrag()
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
        innerAdapter?.setAdapterList(list)
    }


    private fun handleDrag() {
        relContainer.setOnDragListener(object : View.OnDragListener {
            override fun onDrag(v: View?, event: DragEvent?): Boolean {
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

                        val view: RelativeLayout = LayoutInflater.from(this@MainActivity).inflate(
                            R.layout.item_shape, relContainer, false
                        ) as RelativeLayout
//                        shape.setImageResource(state.item.getImageDrawable())
                        view.setX(dropX - state.width as Int / 2)
                        view.setY(dropY - state.height as Int / 2)
                        view.getLayoutParams().width = state.width
                        view.getLayoutParams().height = state.height
                        relContainer.addView(view)
                        view.setOnTouchListener(this@MainActivity)
                        UiUtils.appErrorLog(TAG, "Drop")
                    }


                }
                return true
            }
        })
    }


    var windowwidth = 0
    var windowheight = 0


    private var xDelta = 0
    private var yDelta = 0

    override fun onTouch(v: View?, event: MotionEvent?): Boolean {

        val x = event!!.rawX.toInt()
        val y = event.rawY.toInt()

        when (event.action and MotionEvent.ACTION_MASK) {
            MotionEvent.ACTION_DOWN -> {
                val lParams =  v!!.getLayoutParams() as (RelativeLayout.LayoutParams)
                xDelta = x - lParams.leftMargin
                yDelta = y - lParams.topMargin
            }
            MotionEvent.ACTION_UP -> Toast.makeText(
                this@MainActivity,
                "I'm here!", Toast.LENGTH_SHORT
            )
                .show()
            MotionEvent.ACTION_MOVE -> {
                val layoutParams = v!!.getLayoutParams() as (RelativeLayout.LayoutParams)
                layoutParams.leftMargin = x - xDelta
                layoutParams.topMargin = y - yDelta
                layoutParams.rightMargin = 0
                layoutParams.bottomMargin = 0
                v.setLayoutParams(layoutParams)
            }
        }




        return true
    }
}