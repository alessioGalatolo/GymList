package com.anexus.list

import androidx.core.view.ViewCompat.animate
import android.R.attr.translationY
import android.content.Context
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.core.view.ViewCompat
import androidx.annotation.NonNull
import androidx.coordinatorlayout.widget.CoordinatorLayout
import android.util.AttributeSet
import android.view.View
import kotlin.math.max
import kotlin.math.min

class MusicCardViewBehavior<V : View>(context: Context, attrs: AttributeSet) :
        CoordinatorLayout.Behavior<V>(context, attrs) {

    override fun onStartNestedScroll(coordinatorLayout: CoordinatorLayout,
                                     child: V, directTargetChild: View,
                                     target: View, axes: Int,
                                     type: Int): Boolean {
        return axes == ViewCompat.SCROLL_AXIS_VERTICAL
    }

    override fun onNestedPreScroll(coordinatorLayout: CoordinatorLayout,
                                   child: V, target: View,
                                   dx: Int, dy: Int,
                                   consumed: IntArray,
                                   type: Int) {
        super.onNestedPreScroll(coordinatorLayout, child, target, dx, dy, consumed, type)
        child.translationY = max(1000f , min(child.height.toFloat(), child.translationY + dy)) - 1000f
    }
}
