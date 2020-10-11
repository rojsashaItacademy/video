package ru.trinitydigital.youtube.data

interface LifeCycle<V> {
    fun bind(v: V)
    fun unbind()
}