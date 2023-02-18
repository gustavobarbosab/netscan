package com.network.scanner.news

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.network.scanner.common.netScanToolbar

class NewsFragment : Fragment(R.layout.fragment_news) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        netScanToolbar().title(getString(R.string.news_toolbar))

    }

    companion object {
        fun newInstance() = NewsFragment()
    }
}