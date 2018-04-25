package com.coderwjq.kotlindemo

/**
 * @author: wangjiaqi
 * @date: 2018/4/25
 *
 * 备注:所有的类默认都是不可继承的(final),所以只能继承那些被明确声明open或abstract的类
 */
open class Animal(name: String)

class Person(name: String, surname: String) : Animal(name) {
    init {

    }
}