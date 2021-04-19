package com.coding.acronymism.utils

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity

fun AppCompatActivity.loadFragment(container: Int, fragment: Fragment, isReplace: Boolean = false) {
    if (isReplace)
        this.supportFragmentManager.beginTransaction().replace(container, fragment).commit()
    else
        this.supportFragmentManager.beginTransaction().addToBackStack(fragment.tag)
            .add(container, fragment).commit()
}

fun FragmentActivity.loadFragment(container: Int, fragment: Fragment, isReplace: Boolean = false) {
    if (isReplace)
        this.supportFragmentManager.beginTransaction().replace(container, fragment).commit()
    else
        this.supportFragmentManager.beginTransaction().addToBackStack(fragment.tag)
            .add(container, fragment).commit()
}