package com.Kuhouinn.fetch2rx.util

import com.Kuhouinn.fetch2rx.Convertible
import io.reactivex.Flowable

fun <T> Flowable<T>.toConvertible(): Convertible<T> {
    return Convertible(this)
}