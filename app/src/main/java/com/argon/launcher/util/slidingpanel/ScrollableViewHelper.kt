package com.argon.launcher.util.slidingpanel

import android.view.View
import android.widget.ListView
import android.widget.ScrollView
import androidx.recyclerview.widget.RecyclerView


class ScrollableViewHelper {
    /**
     * Returns the current scroll position of the scrollable view. If this method returns zero or
     * less, it means at the scrollable view is in a position such as the panel should handle
     * scrolling. If the method returns anything above zero, then the panel will let the scrollable
     * view handle the scrolling
     *
     * @param scrollableView the scrollable view
     * @param isSlidingUp whether or not the panel is sliding up or down
     * @return the scroll position
     */
    fun getScrollableViewScrollPosition(scrollableView: View?, isSlidingUp: Boolean): Int {
        if (scrollableView == null) return 0
        if (scrollableView is ScrollView) {
            if (isSlidingUp) {
                return scrollableView.getScrollY()
            } else {
                val sv = scrollableView
                val child = sv.getChildAt(0)
                return (child.bottom - (sv.height + sv.scrollY))
            }
        } else if (scrollableView is ListView && scrollableView.childCount > 0) {
            if (scrollableView.adapter == null) return 0
            if (isSlidingUp) {
                val firstChild = scrollableView.getChildAt(0)
                // Approximate the scroll position based on the top child and the first visible item
                return scrollableView.firstVisiblePosition * firstChild.height - firstChild.top
            } else {
                val lastChild = scrollableView.getChildAt(scrollableView.childCount - 1)
                // Approximate the scroll position based on the bottom child and the last visible item
                return (scrollableView.adapter.count - scrollableView.lastVisiblePosition - 1) * lastChild.height + lastChild.bottom - scrollableView.bottom
            }
        } else if (scrollableView is RecyclerView && scrollableView.childCount > 0) {
            val lm = scrollableView.layoutManager
            if (scrollableView.adapter == null) return 0
            if (isSlidingUp) {
                val firstChild = scrollableView.getChildAt(0)
                // Approximate the scroll position based on the top child and the first visible item
                return scrollableView.getChildLayoutPosition(firstChild) * lm!!.getDecoratedMeasuredHeight(
                    firstChild
                ) - lm.getDecoratedTop(firstChild)
            } else {
                val lastChild = scrollableView.getChildAt(scrollableView.childCount - 1)
                // Approximate the scroll position based on the bottom child and the last visible item
                return (scrollableView.adapter!!.itemCount - 1) * lm!!.getDecoratedMeasuredHeight(
                    lastChild
                ) + lm.getDecoratedBottom(
                    lastChild
                ) - scrollableView.bottom
            }
        } else {
            return 0
        }
    }
}