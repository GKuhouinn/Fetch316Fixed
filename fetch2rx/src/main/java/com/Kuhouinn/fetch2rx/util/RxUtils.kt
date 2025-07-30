package com.GKuhouinn.fetch2rx.util

import com.GKuhouinn.fetch2rx.Convertible
import io.reactivex.Flowable

fun <T> Flowable<T>.toConvertible(): Convertible<T> {
    return Convertible(this)
}