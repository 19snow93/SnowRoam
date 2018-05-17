package com.rhythm7.snowroam.di.module

import com.rhythm7.snowroam.ui.editor.source.EditSourceFragment
import com.rhythm7.snowroam.ui.main.home.HomeFragment
import com.rhythm7.snowroam.ui.main.leftmenu.LeftMenuFragment
import com.rhythm7.snowroam.ui.trash.TrashFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * 所有使用Dagger2 的fragment 都需要在这个类里注册
 */
@Module
abstract class FragmentModule {
    @ContributesAndroidInjector
    abstract fun getMainFragment(): HomeFragment

    @ContributesAndroidInjector
    abstract fun getTrashFragment(): TrashFragment

    @ContributesAndroidInjector
    abstract fun getEditSourceFragment(): EditSourceFragment

    @ContributesAndroidInjector
    abstract fun getLeftMenuFragment(): LeftMenuFragment
}
