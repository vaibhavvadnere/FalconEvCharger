package com.falcon.evCharger.Scanning

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import androidx.core.content.ContextCompat
import com.falcon.evcharger.R
import com.journeyapps.barcodescanner.DecoratedBarcodeView

class CustomViewfinderView : DecoratedBarcodeView {

    private val paint = Paint()

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    override fun dispatchDraw(canvas: Canvas) {
        super.dispatchDraw(canvas)

        val width = width.toFloat()
        val height = height.toFloat()

        // Calculate framing rectangle dimensions
        val aspectRatio = 4f / 4f // Example aspect ratio (width / height)
        val frameWidth = if (width < height) width * 0.8f else height * 0.8f
        val frameHeight = frameWidth / aspectRatio

        // Adjust top and bottom to center the frame vertically and increase height
        val verticalOffset = 20f // Adjust the offset as needed
        val top = (height - frameHeight) / 2 - verticalOffset
        val bottom = top + frameHeight + verticalOffset * 2 // Increase height by offset

        // Adjust left and right to center the frame horizontally
        val left = (width - frameWidth) / 2
        val right = left + frameWidth

        // Draw a border around the frame
        paint.color =
            ContextCompat.getColor(context, R.color.green_900) // Set the desired border color
        paint.strokeWidth = 10f // Set the border width
        paint.style = Paint.Style.STROKE // Set the style to stroke
        canvas.drawRect(left, top, right, bottom, paint)
    }
}