/*
 * Copyright (C) 2018 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.rhythm7.snowroam.di.module

import android.arch.lifecycle.ViewModel
import com.rhythm7.core.di.qualifier.ViewModelKey
import com.rhythm7.snowroam.ui.editor.EditorViewModel
import com.rhythm7.snowroam.ui.main.MainViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {
    @Binds @IntoMap @ViewModelKey(MainViewModel::class)
    abstract fun bindViewModel0(viewModel: MainViewModel): ViewModel


    @Binds @IntoMap @ViewModelKey(EditorViewModel::class)
    abstract fun bindViewModel1(viewModel: EditorViewModel): ViewModel
}
