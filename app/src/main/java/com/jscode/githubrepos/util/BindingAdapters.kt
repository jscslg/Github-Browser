package com.jscode.githubrepos.util

import android.os.Build
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import com.bumptech.glide.request.RequestOptions
import java.lang.Exception
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle

@BindingAdapter("imageUrl")
fun bindImage(img: ImageView, url:String?){
    url?.let{
        val imgUri= url.toUri().buildUpon().scheme("https").build()
        GlideApp.with(img.context)
            .load(imgUri)
            .apply(RequestOptions.circleCropTransform())
            .into(img)
    }
}

@BindingAdapter("formatDateText")
fun bindText(txt: TextView, date:String?){
    date?.let {
            try {
                val data = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
                        ZonedDateTime.parse(date).format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM)) else date
                txt.text = data
            } catch (e: Exception){
                Log.e("Binding Adapter", "DATE - $date not in correct format")
            }
    }
}