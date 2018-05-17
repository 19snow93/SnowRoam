package com.rhythm7.snowroam.ui.main

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.view.GravityCompat
import android.support.v4.view.ViewPager
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import com.alibaba.android.arouter.facade.annotation.Route
import com.rhythm7.core.base.activity.BaseDaggerActivity
import com.rhythm7.core.di.module.ViewModelFactory
import com.rhythm7.core.utlis.StatusBarUtil
import com.rhythm7.core.utlis.delegates.AutoDisposable
import com.rhythm7.core.utlis.ext.*
import com.rhythm7.core.utlis.getTransitionColor
import com.rhythm7.mid.router.ACT_EDIT_SOURCE
import com.rhythm7.mid.router.ACT_MAIN
import com.rhythm7.mid.router.FRAG_MAIN
import com.rhythm7.mid.router.FRAG_TRASH
import com.rhythm7.snowroam.R
import com.rhythm7.snowroam.ui.main.leftmenu.LeftMenuFragment
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_main.*
import javax.inject.Inject

/**
 * 首页
 */
@Route(path = ACT_MAIN)
class MainActivity : BaseDaggerActivity() {

    @Inject
    lateinit var mViewModelFactory: ViewModelFactory

    private var mMainViewModel: MainViewModel by AutoDisposable()

    private val NAV_MENU_WIDTH = 200f.dp2px()

    private var mCurrentFragmentPage = 0

    private val mLightColor by lazy { R.color.white_1000.resIdToColor()  }
    private val mDarkColor by lazy { R.color.grey_700.resIdToColor() }
    private val mPrimaryColor by lazy { R.color.colorPrimary.resIdToColor() }

    private var mMenu: Menu? = null

    override fun initViewAndEvent() {
        initView()

        initEvent()
    }

    override fun getLayoutId(): Int = R.layout.activity_main

    /**
     * 初始化控件
     */
    private fun initView() {
        setToolbar(title = R.string.app_name.resIdToString())

        StatusBarUtil.setColor(this@MainActivity, mLightColor, 0)

        _drawable_main.setScrimColor(0x00ffffff) //取消底部变暗
        _drawable_main.drawerElevation = 0f //去除阴影

        val toggle = ActionBarDrawerToggle(this, _drawable_main, getToolbar(),
                R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        _drawable_main.addDrawerListener(toggle)
        toggle.syncState()

        _drawable_main.addDrawerListener(getDrawableListener())

        _vp_main.adapter = getFragmentAdapter()

        _vp_main.addOnPageChangeListener(getPagerChangeAdapter())

        addFragment(LeftMenuFragment(), R.id._fragment_container)

    }


    /**
     * 初始化事件
     */
    private fun initEvent() {
        mMainViewModel = ViewModelProviders.of(this, mViewModelFactory).get(MainViewModel::class.java)

        mMainViewModel.categoryId.observe(this, Observer {
            _drawable_main.closeDrawers()
        })
    }


    override fun onBackPressed() {
        if (_drawable_main.isDrawerOpen(GravityCompat.START)) {
            _drawable_main.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }


    /**
     * drawable 监听事件
     */
    private fun getDrawableListener(): DrawerLayout.DrawerListener {
        return object : DrawerLayout.DrawerListener {
            override fun onDrawerStateChanged(newState: Int) {
            }

            override fun onDrawerSlide(drawerView: View, slideOffset: Float) {
                if (slideOffset < 0.01f) {
                    StatusBarUtil.setColor(this@MainActivity,
                            if (mCurrentFragmentPage == 0) mLightColor else mDarkColor,
                            0)
                } else if (slideOffset < 0.25f){
                    StatusBarUtil.setColor(this@MainActivity, mPrimaryColor, 0)
                }

                app_bar_main.scaleX = (1 - slideOffset * 0.1f)
                app_bar_main.scaleY = (1- slideOffset * 0.1f)
                app_bar_main.translationX = slideOffset * NAV_MENU_WIDTH * 0.9f
            }

            override fun onDrawerClosed(drawerView: View) {
            }

            override fun onDrawerOpened(drawerView: View) {
            }
        }
    }

    /**
     * fragmentPagerAdapter
     */
    private fun getFragmentAdapter(): FragmentPagerAdapter {
        return object : FragmentPagerAdapter(supportFragmentManager) {
            override fun getCount(): Int  = 2

            override fun getItem(position: Int): Fragment {
                return when (position) {
                    0 -> navigateToFragment(FRAG_MAIN)
                    else -> navigateToFragment(FRAG_TRASH)
                }
            }
        }
    }


    /**
     * fragment切换监听
     */
    private fun getPagerChangeAdapter(): ViewPager.OnPageChangeListener {
        return object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {

            }
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
                val bgColor: Int
                val fgColor: Int

                if (position == 0) {
                    bgColor = getTransitionColor(positionOffset, mLightColor, mDarkColor)
                    fgColor = getTransitionColor(positionOffset, mPrimaryColor, mLightColor)
                } else {
                    bgColor = getTransitionColor(positionOffset, mDarkColor, mLightColor)
                    fgColor = getTransitionColor(positionOffset, mLightColor, mPrimaryColor)
                }
                getToolbar().setBackgroundColor(bgColor)
                StatusBarUtil.setColor(this@MainActivity, bgColor, 0)
                setToolbarForeground(fgColor)
            }
            override fun onPageSelected(position: Int) {
                mCurrentFragmentPage = position

                mMenu?.findItem(R.id.action_add)?.isVisible = position == 0
            }
        }
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        mMenu = menu
        menuInflater?.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == R.id.action_add) {
            navigateToActivity(ACT_EDIT_SOURCE)
            return true
        }
        return super.onOptionsItemSelected(item)
    }


    override fun onResume() {
        super.onResume()
        mMainViewModel.loadCategoryList()
        mMainViewModel.loadNoteList()
    }

}
