package com.example.newsapp.main

import android.os.Bundle
import android.view.View

import com.example.newsapp.BaseFragment
import com.example.newsapp.R

class MainFragment : BaseFragment(), MainView {
    private lateinit var presenter: MainPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        presenter = MainPresenterImpl()
        presenter.takeView(this)
    }

    override fun layoutRes() = R.layout.fragment_main

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // TODO: Implement initial presenter action here
    }

    override fun onDestroy() {
        presenter.dropView()
        super.onDestroy()
    }

    companion object {
        fun newInstance(): MainFragment {
            return MainFragment()
        }
    }
}
