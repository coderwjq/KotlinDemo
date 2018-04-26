package com.coderwjq.kotlindemo

/**
 * Created by wangjiaqi on 2018/4/26
 */
public interface Command<T> {
    fun execute(): T
}