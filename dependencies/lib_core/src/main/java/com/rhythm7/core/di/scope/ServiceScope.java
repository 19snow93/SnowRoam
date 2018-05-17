package com.rhythm7.core.di.scope;

import java.lang.annotation.Retention;

import javax.inject.Scope;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Created by Administrator on 2017/9/11.
 */
@Scope
@Retention(RUNTIME)
public @interface ServiceScope {
}
