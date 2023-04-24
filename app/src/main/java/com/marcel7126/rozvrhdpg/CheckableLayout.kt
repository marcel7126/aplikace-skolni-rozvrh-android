package com.marcel7126.rozvrhdpg

import android.widget.Checkable
import android.content.Context
import android.content.res.ColorStateList
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.core.view.updateMarginsRelative
import androidx.core.widget.ImageViewCompat
import com.marcel7126.rozvrhdpg.databinding.CheckableLayoutBinding

abstract class CheckableLayout(context: Context, attrs: AttributeSet? = null) : ConstraintLayout(context, attrs), Checkable {
/*
    private val _binding: CheckableLayoutBinding = CheckableLayoutBinding.inflate(LayoutInflater.from(context))

    val binding get() = _binding

    init {
        //View.inflate(context, R.layout.layout_button, this)
        //binding = ResultProfileBinding.inflate(LayoutInflater.from(context), this)
        applyAttrs(attrs)
    }

    private fun applyAttrs(attrs: AttributeSet?) {
        // attributes to typed array
        val typedArray = context.theme.obtainStyledAttributes(attrs, R.styleable.LayoutButton, 0, 0)

        // left icon color attribute
        val iconTintColorResource = typedArray.getResourceId(R.styleable.LayoutButton_iconTint, android.R.color.black) // todo color
        val iconTintColor = ContextCompat.getColor(context, iconTintColorResource)
        ImageViewCompat.setImageTintList(binding.iconStart, ColorStateList.valueOf(iconTintColor))
        // set left icon drawable from attribute
        val iconDrawable = typedArray.getDrawable(R.styleable.LayoutButton_icon)
        binding.iconStart.setImageDrawable(iconDrawable)
        if (iconDrawable == null) {
            val params = (binding.textMain.layoutParams as ViewGroup.MarginLayoutParams)
            params.updateMarginsRelative(start = 0)
        }

        // right icon color attribute
        val iconActionTintColorResource = typedArray.getResourceId(R.styleable.LayoutButton_iconActionTint, android.R.color.black) // todo: color
        val iconActionTintColor = ContextCompat.getColor(context, iconActionTintColorResource)
        ImageViewCompat.setImageTintList(binding.iconEnd, ColorStateList.valueOf(iconActionTintColor))
        // set right icon drawable from attribute
        val iconActionDrawable = typedArray.getDrawable(R.styleable.LayoutButton_iconAction)
        binding.iconEnd.setImageDrawable(iconActionDrawable)
        if (iconActionDrawable == null) {
            val params = (binding.textSecondary.layoutParams as ViewGroup.MarginLayoutParams)
            params.updateMarginsRelative(end = 0)
        }

        // set main and secondary texts
        binding.textMain.text = typedArray.getString(R.styleable.LayoutButton_textMain)
        binding.textSecondary.text = typedArray.getString(R.styleable.LayoutButton_textSecondary)

        // recycle typed array
        typedArray.recycle()
    }

    var mainText: CharSequence
        get() = binding.textMain.text
        set(text: CharSequence) {
            binding.textMain.text = text
        }

    var secondaryText: CharSequence
        get() = binding.textSecondary.text
        set(text: CharSequence) {
            binding.textSecondary.text = text
        }
*/
}