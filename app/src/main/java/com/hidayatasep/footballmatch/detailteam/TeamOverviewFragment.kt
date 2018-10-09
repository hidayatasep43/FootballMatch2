package com.hidayatasep.footballmatch.detailteam


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.hidayatasep.footballmatch.R

// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM = "param"

class TeamOverviewFragment : Fragment() {

    private lateinit var overview: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            overview = it.getString(ARG_PARAM)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_team_overview, container, false)
    }


    companion object {
        @JvmStatic
        fun newInstance(overview: String? = "") =
                TeamOverviewFragment().apply {
                    arguments = Bundle().apply {
                        putString(ARG_PARAM, overview)
                    }
                }
    }
}
