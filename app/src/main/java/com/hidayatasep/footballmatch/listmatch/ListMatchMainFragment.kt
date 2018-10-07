package com.hidayatasep.footballmatch.listmatch


import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.view.ViewPager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.gson.Gson
import com.hidayatasep.footballmatch.R
import com.hidayatasep.latihan2.ApiRepository


class ListMatchMainFragment : Fragment() {

    private lateinit var mPagerAdapter: ListMatchPagerAdapter
    private lateinit var mViewPager: ViewPager
    private lateinit var mTabLayout: TabLayout
    private lateinit var listMatchFragmentPrev: ListMatchFragment
    private lateinit var listMatchFragmentNext: ListMatchFragment
    private lateinit var listMatchPresenterPrev: ListMatchPresenter
    private lateinit var listMatchPresenterNext: ListMatchPresenter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_list_match_main, container, false)
        mViewPager = view.findViewById(R.id.viewpager)
        mTabLayout = view.findViewById(R.id.tablayout)
        val request = ApiRepository()
        val gson = Gson()

        listMatchFragmentPrev = ListMatchFragment.newInstance()
        listMatchFragmentNext = ListMatchFragment.newInstance()
        listMatchPresenterPrev = ListMatchPresenter(listMatchFragmentPrev, request, gson, TYPE_LIST_PREV)
        listMatchPresenterNext = ListMatchPresenter(listMatchFragmentNext, request, gson, TYPE_LIST_NEXT)

        mPagerAdapter = ListMatchPagerAdapter(childFragmentManager, listMatchFragmentPrev, listMatchFragmentNext)
        mViewPager.adapter = mPagerAdapter
        mTabLayout.setupWithViewPager(mViewPager)

        return view
    }


    companion object {
        const val TYPE_LIST_PREV = 1
        const val TYPE_LIST_NEXT = 2

        @JvmStatic
        fun newInstance() =
                ListMatchMainFragment()

    }

    class ListMatchPagerAdapter (
            fragmentManager: FragmentManager,
            val listMatchFragmentPrev: ListMatchFragment,
            val listMatchFragmentNext: ListMatchFragment)
        : FragmentPagerAdapter(fragmentManager) {

        override fun getItem(position: Int): Fragment? {
            return if (position == 0) {
                listMatchFragmentNext
            } else {
                listMatchFragmentPrev
            }
        }

        override fun getPageTitle(position: Int): CharSequence? {
            return if (position == 0) {
                "NEXT"
            } else {
                "LAST"
            }
        }

        override fun getCount(): Int = 2
    }

}
