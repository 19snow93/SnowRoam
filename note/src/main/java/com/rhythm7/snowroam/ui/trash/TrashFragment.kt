package com.rhythm7.snowroam.ui.trash


import com.alibaba.android.arouter.facade.annotation.Route
import com.rhythm7.core.base.fragment.BaseMVPFragment
import com.rhythm7.mid.router.FRAG_MAIN
import com.rhythm7.mid.router.FRAG_TRASH
import com.rhythm7.snowroam.R

/**
 * Created by Jaminchanks on 2018-03-30.
 */

@Route(path = FRAG_TRASH)
class TrashFragment : BaseMVPFragment<TrashContract.View, TrashPresenter>(), TrashContract.View {
    override fun initViewAndEvent() {

    }

    override fun getLayoutId(): Int = R.layout.fragment_trash
}