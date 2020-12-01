package com.cjg.floatingwindow

import android.annotation.SuppressLint
import android.app.Service
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.PixelFormat
import android.os.Build
import android.os.IBinder
import android.provider.Settings
import android.view.Gravity
import android.view.MotionEvent
import android.view.View
import android.view.View.OnTouchListener
import android.view.WindowManager
import android.widget.Button
import androidx.annotation.Nullable
import androidx.annotation.RequiresApi


/**
 * @描述
 * @author chenjinguang
 * @创建时间 2020/12/1
 * @修改人和其它信息
 */
class FloatingButtonService : Service() {

    companion object{
        var isStarted = false
    }

    private var windowManager: WindowManager? = null
    private var layoutParams: WindowManager.LayoutParams? = null

    private var button: Button? = null

    override fun onCreate() {
        super.onCreate()
        isStarted = true
        windowManager = getSystemService(Context.WINDOW_SERVICE) as WindowManager
        layoutParams = WindowManager.LayoutParams()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            layoutParams!!.type = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY
        } else {
            layoutParams!!.type = WindowManager.LayoutParams.TYPE_PHONE
        }
        layoutParams!!.format = PixelFormat.RGBA_8888
        layoutParams!!.gravity = Gravity.LEFT or Gravity.TOP
        layoutParams!!.flags =
            WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL or WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
        layoutParams!!.width = 500
        layoutParams!!.height = 100
        layoutParams!!.x = 300
        layoutParams!!.y = 300
    }

    @Nullable
    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        showFloatingWindow()
        return super.onStartCommand(intent, flags, startId)
    }

    @SuppressLint("ClickableViewAccessibility")
    @RequiresApi(Build.VERSION_CODES.M)
    private fun showFloatingWindow() {
        if (Settings.canDrawOverlays(this)) {
            button = Button(applicationContext)
            button!!.text = "Floating Window"
            button!!.setBackgroundColor(Color.BLUE)
            windowManager!!.addView(button, layoutParams)
            button!!.setOnTouchListener(@SuppressLint("ClickableViewAccessibility")
            object: OnTouchListener{
                private var x = 0
                private var y = 0
                override fun onTouch(view: View, event: MotionEvent): Boolean {
                    when (event.action) {
                        MotionEvent.ACTION_DOWN -> {
                            x = event.rawX.toInt()
                            y = event.rawY.toInt()
                        }
                        MotionEvent.ACTION_MOVE -> {
                            val nowX = event.rawX.toInt()
                            val nowY = event.rawY.toInt()
                            val movedX = nowX - x
                            val movedY = nowY - y
                            x = nowX
                            y = nowY
                            layoutParams?.x = layoutParams?.x?.plus(movedX)
                            layoutParams?.y = layoutParams?.y?.plus(movedY)
                            windowManager?.updateViewLayout(view, layoutParams)
                        }
                        else -> {
                        }
                    }
                    return false
                }

            })
        }
    }

}