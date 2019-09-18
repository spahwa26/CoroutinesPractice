package com.example.stripedemokotlin.utils

import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.BlendMode
import android.graphics.BlendModeColorFilter
import android.graphics.PorterDuff
import android.os.Build
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.stripedemokotlin.R
import com.google.gson.Gson
import okhttp3.ResponseBody
import kotlin.reflect.KClass


fun Context.toast(message: String) {
    val toast = Toast.makeText(this, message, Toast.LENGTH_SHORT)
    val view = toast.view
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
        view.background.colorFilter =
            BlendModeColorFilter(ContextCompat.getColor(this, R.color.colorPrimary), BlendMode.SRC_ATOP)
    } else {
        view.background.setColorFilter(ContextCompat.getColor(this, R.color.colorPrimary), PorterDuff.Mode.SRC_ATOP)
    }
    val text: TextView = view.findViewById(android.R.id.message)
    text.setTextColor(ContextCompat.getColor(this, R.color.colorWhite))
    toast.show()
}


fun Context.hideKeyboard(view: View) {
    val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
}

fun Context.showKeyboard(view: View) {
    val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.showSoftInput(view, 0)
}

fun Fragment.hasPermission(permission : String)
         = ActivityCompat.checkSelfPermission(context!!, permission) == PackageManager.PERMISSION_GRANTED

fun <T : Any>ResponseBody.convertToPojo(pojo: KClass<T>): T {
    return Gson().fromJson(this.string(), pojo.java)
}