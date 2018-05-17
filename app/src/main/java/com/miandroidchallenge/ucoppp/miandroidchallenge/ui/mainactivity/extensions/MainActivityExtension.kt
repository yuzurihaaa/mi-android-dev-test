package com.miandroidchallenge.ucoppp.miandroidchallenge.ui.mainactivity.extensions

import com.miandroidchallenge.ucoppp.miandroidchallenge.application.MyApplication
import com.miandroidchallenge.ucoppp.miandroidchallenge.ui.mainactivity.MainActivity

fun MainActivity.injectDagger() {
    (application as MyApplication)
            .appComponent
            .inject(this)
}