package com.marcel7126.rozvrhdpg

import android.content.Context
import android.util.AttributeSet
import android.widget.Button
import android.widget.Checkable







class CheckableButton: com.google.android.material.button.MaterialButton, Checkable {
    private var mChecked = false

    constructor(context: Context): super(context) {}
    constructor(context: Context, attrs: AttributeSet?): super(context, attrs) {}
    constructor(context: Context, attrs: AttributeSet?, defStyle: Int): super(
        context,
        attrs,
        defStyle
    ) {}

    override fun isChecked(): Boolean {
        return mChecked
    }

    override fun setChecked(checked: Boolean) {
        mChecked = checked
        refreshDrawableState()
    }

    override fun toggle() {
        mChecked = !mChecked
        refreshDrawableState()
    }

    protected override fun onCreateDrawableState(extraSpace: Int): IntArray {
        val drawableState: IntArray = super.onCreateDrawableState(extraSpace + 1)
        if (isChecked) {
            mergeDrawableStates(drawableState, CheckedStateSet)
        }
        return drawableState
    }

    companion object {
        private val CheckedStateSet = intArrayOf(android.R.attr.state_checked)
    }
}