package com.cjg.floatingwindow

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 0) {
            if (!Settings.canDrawOverlays(this)) {
                Toast.makeText(this, "授权失败", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "授权成功", Toast.LENGTH_SHORT).show()
                startService(Intent(this@MainActivity, FloatingButtonService::class.java))
            }
        }
        else if (requestCode == 1) {
            if (!Settings.canDrawOverlays(this)) {
                Toast.makeText(this, "授权失败", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "授权成功", Toast.LENGTH_SHORT).show()
                startService(Intent(this@MainActivity, FloatingImageDisplayService::class.java))
            }
        } else if (requestCode == 2) {
            if (!Settings.canDrawOverlays(this)) {
                Toast.makeText(this, "授权失败", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "授权成功", Toast.LENGTH_SHORT).show()
                startService(Intent(this@MainActivity, FloatingVideoService::class.java))
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    fun startFloatingButtonService(view: View?) {
        if (FloatingButtonService.isStarted) {
            return
        }
        if (!Settings.canDrawOverlays(this)) {
            Toast.makeText(this, "当前无权限，请授权", Toast.LENGTH_SHORT)
            startActivityForResult(Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:$packageName")), 0)
        } else {
            startService(Intent(this@MainActivity, FloatingButtonService::class.java))
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    fun startFloatingImageDisplayService(view: View?) {
        if (FloatingImageDisplayService.isStarted) {
            return
        }
        if (!Settings.canDrawOverlays(this)) {
            Toast.makeText(this, "当前无权限，请授权", Toast.LENGTH_SHORT)
            startActivityForResult(Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:$packageName")), 1)
        } else {
            startService(Intent(this@MainActivity, FloatingImageDisplayService::class.java))
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    fun startFloatingVideoService(view: View?) {
        if (FloatingVideoService.isStarted) {
            return
        }
        if (!Settings.canDrawOverlays(this)) {
            Toast.makeText(this, "当前无权限，请授权", Toast.LENGTH_SHORT)
            startActivityForResult(Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:$packageName")), 2)
        } else {
            startService(Intent(this@MainActivity, FloatingVideoService::class.java))
        }
    }

}