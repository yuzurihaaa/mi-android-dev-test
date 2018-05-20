package com.miandroidchallenge.ucoppp.miandroidchallenge.ui.unselecteddetailsfragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.miandroidchallenge.ucoppp.miandroidchallenge.R

class UnselectedDetailsFragment : Fragment() {

    companion object {
        fun newInstance(): UnselectedDetailsFragment = UnselectedDetailsFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_unselected_details, container, false)
    }
}