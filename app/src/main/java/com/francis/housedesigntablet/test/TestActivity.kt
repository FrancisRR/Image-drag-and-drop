package com.francis.housedesigntablet.test

import android.os.Bundle
import android.view.DragEvent
import android.view.MotionEvent
import android.view.View
import android.widget.RelativeLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import com.francis.housedesigntablet.R
import com.francis.housedesigntablet.utils.UiUtils
import kotlinx.android.synthetic.main.activity_test.*


class TestActivity : AppCompatActivity(), View.OnClickListener, View.OnLongClickListener,
    View.OnDragListener, View.OnTouchListener {
    private val TAG by lazy { TestActivity::class.java.simpleName }
    private var selectedId: Int = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)

        //  imageView.setOnLongClickListener(this)
        //imageView2.setOnLongClickListener(this)
        // parentId.setOnLongClickListener(this)

        imageView.setOnTouchListener(this)
        imageView2.setOnTouchListener(this)
        //  parentId.setOnDragListener(this)


    }

    var windowwidth = 0
    var windowheight = 0

    private val layoutParams: RelativeLayout.LayoutParams? = null


    override fun onTouch(v: View?, event: MotionEvent?): Boolean {
        windowwidth = windowManager.defaultDisplay.width
        windowheight = windowManager.defaultDisplay.height
        val layoutParams = v?.getLayoutParams() as RelativeLayout.LayoutParams
        when (event?.action) {
            MotionEvent.ACTION_DOWN -> {
            }
            MotionEvent.ACTION_MOVE -> {
                var x_cord = event.rawX.toInt()
                var y_cord = event.rawY.toInt()
                if (x_cord > windowwidth) {
                    x_cord = windowwidth
                }
                if (y_cord > windowheight) {
                    y_cord = windowheight
                }
                layoutParams.leftMargin = x_cord - 150
                layoutParams.topMargin = y_cord -350
                v?.setLayoutParams(layoutParams)
            }

        }
        return true
    }

    override fun onClick(v: View?) {
        selectedId = v?.id!!
        UiUtils.appErrorLog(TAG, "ID:$selectedId")
    }

    override fun onLongClick(v: View?): Boolean {
        /* val item = ClipData.Item(v!!.tag as CharSequence)
         val mimeTypes = arrayOf(ClipDescription.MIMETYPE_TEXT_PLAIN)
         val clipData = ClipData(v?.getTag().toString(), mimeTypes, item)*/
        val dragShowdowBuilder = View.DragShadowBuilder(v)
        ViewCompat.startDragAndDrop(v!!, null, dragShowdowBuilder, null, 0)
        return true
    }

    override fun onDrag(v: View?, event: DragEvent?): Boolean {
        var layoutParam: RelativeLayout.LayoutParams? = null
        when (event?.action) {
            DragEvent.ACTION_DRAG_STARTED -> {
                layoutParam = v?.layoutParams as RelativeLayout.LayoutParams
            }
            DragEvent.ACTION_DRAG_ENDED -> {
                val x = event.x.toInt()
                val y = event.y.toInt()
                layoutParam?.leftMargin = x
                layoutParam?.topMargin = y
                v?.layoutParams = layoutParam
            }
        }

        return true
    }
}