package com.example.newsapp.utils.presenter

open class BasePresenter<V> : Presenter<V> {
    private var view: V? = null

    override fun takeView(view: V) {
        this.view = view
    }

    override fun dropView() {
        view = null // NOPMD
    }

    fun hasView() = view != null

    fun onView(action: V.() -> Unit) {
        if (hasView()) {
            action.invoke(view!!)
        }
    }
}
