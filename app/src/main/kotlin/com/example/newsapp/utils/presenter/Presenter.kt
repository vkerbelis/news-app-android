package com.example.newsapp.utils.presenter

interface Presenter<in V> {
    fun takeView(view: V)

    fun dropView()
}
