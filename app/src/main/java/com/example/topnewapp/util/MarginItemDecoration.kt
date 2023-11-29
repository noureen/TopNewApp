package com.example.topnewapp.util

import android.content.Context
import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ItemDecoration


class MarginItemDecoration(
    context: Context,
    topMarginInDp: Int,
    bottomMarginInDp: Int,
    leftMarginInDp: Int,
    rightMarginInDp: Int
) :
    ItemDecoration() {
    private val marginTop: Int
    private val marginBottom: Int
    private val marginLeft: Int
    private val marginRight: Int

    init {
        marginTop = (context.resources.displayMetrics.density * topMarginInDp).toInt()
        marginBottom = (context.resources.displayMetrics.density * bottomMarginInDp).toInt()
        marginLeft = (context.resources.displayMetrics.density * leftMarginInDp).toInt()
        marginRight = (context.resources.displayMetrics.density * rightMarginInDp).toInt()
    }

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        outRect.top = marginTop
        outRect.bottom = marginBottom
        outRect.left = marginLeft
        outRect.right = marginRight
    }
}
