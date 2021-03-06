package com.github.giacomoparisi.droidbox.recycler.decoration

import android.content.Context
import android.graphics.Rect
import android.view.View
import com.github.giacomoparisi.droidbox.utility.dpToPx

/**
 * Created by Giacomo Parisi on 19/10/17.
 * https://github.com/giacomoParisi
 */
class DroidItemDecoration(
        private val context: Context,
        private val listSize: Int) : androidx.recyclerview.widget.RecyclerView.ItemDecoration() {

    var leftMarginDp: Float = 0.0f
        set(value) {
            field = dpToPx(value, context)
        }
    var rightMarginDp: Float = 0.0f
        set(value) {
            field = dpToPx(value, context)
        }

    override fun getItemOffsets(
            outRect: Rect,
            view: View,
            parent: androidx.recyclerview.widget.RecyclerView,
            state: androidx.recyclerview.widget.RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)

        if (outRect != null) {
            outRect.left = leftMarginDp.toInt()
        }
        if (outRect != null) {
            outRect.right = rightMarginDp.toInt()
        }
    }
}