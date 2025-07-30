@file:JvmName("OkHttpUtils")

package com.GKuhouinn.fetch2okhttp

import com.GKuhouinn.fetch2core.getDefaultCookieManager
import okhttp3.CookieJar
import okhttp3.JavaNetCookieJar

fun getDefaultCookieJar(): CookieJar {
    val cookieManager = getDefaultCookieManager()
    return JavaNetCookieJar(cookieManager)
}