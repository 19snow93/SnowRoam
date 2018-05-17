package com.rhythm7.core.base.mvp

import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

/**
 * 负责自动取消Rx操作
 */
interface RxAutoDisposable {

    fun Disposable.addToDisposable() {
        mCompositeDisposable?.add(this)
    }

    fun Disposable?.removeDisposable() {
        if (this == null) return

        if (this.isDisposed) {
            this.dispose()
        }
        mCompositeDisposable?.remove(this)
    }

    fun clearAllDisposable() {
        mCompositeDisposable?.clear()
    }

    companion object {
        protected val mCompositeDisposable: CompositeDisposable? = null
            get() = field ?: CompositeDisposable()
    }

}
