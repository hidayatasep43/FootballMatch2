package com.hidayatasep.footballmatch.listfavorite


import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.view.ViewPager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.hidayatasep.footballmatch.R


class ListFavoriteMainFragment : Fragment() {

    private lateinit var mPagerAdapter: ListFavoritePagerAdapter
    private lateinit var mViewPager: ViewPager
    private lateinit var mTabLayout: TabLayout
    private lateinit var listFavoriteMatchFragment: ListFavoriteFragment
    private lateinit var listFavoriteTeamFragment: ListFavoriteFragment
    private lateinit var listFavoriteMatchPresenter: ListFavoritePresenter
    private lateinit var listFavoriteTeamPresenter: ListFavoritePresenter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val view =  inflater.inflate(R.layout.fragment_list_favorite_main, container, false)
        mViewPager = view.findViewById(R.id.viewpager)
        mTabLayout = view.findViewById(R.id.tablayout)

        listFavoriteMatchFragment = ListFavoriteFragment.newInstance(TYPE_FAVORITE_MATCH)
        listFavoriteTeamFragment = ListFavoriteFragment.newInstance(TYPE_FAVORITE_TEAM)
        listFavoriteMatchPresenter = ListFavoritePresenter(context as FragmentActivity, listFavoriteMatchFragment, TYPE_FAVORITE_MATCH)
        listFavoriteTeamPresenter = ListFavoritePresenter(context as FragmentActivity, listFavoriteTeamFragment, TYPE_FAVORITE_TEAM)

        mPagerAdapter = ListFavoritePagerAdapter(childFragmentManager, listFavoriteMatchFragment, listFavoriteTeamFragment)
        mViewPager.adapter = mPagerAdapter
        mTabLayout.setupWithViewPager(mViewPager)

        return view
    }

    companion object {
        const val TYPE_FAVORITE_MATCH = 1
        const val TYPE_FAVORITE_TEAM = 2

        @JvmStatic
        fun newInstance() =
                ListFavoriteMainFragment()
    }

    class ListFavoritePagerAdapter (
            fragmentManager: FragmentManager,
            val listFavoriteMatchFragment: ListFavoriteFragment,
            val listFavoriteTeamFragment: ListFavoriteFragment)
        : FragmentPagerAdapter(fragmentManager) {

        override fun getItem(position: Int): Fragment? {
            return if (position == 0) {
                listFavoriteMatchFragment
            } else {
                listFavoriteTeamFragment
            }
        }

        override fun getPageTitle(position: Int): CharSequence? {
            return if (position == 0) {
                "MATCHES"
            } else {
                "TEAM"
            }
        }

        override fun getCount(): Int = 2
    }
}
