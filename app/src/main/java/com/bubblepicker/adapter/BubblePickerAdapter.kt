package com.bubblepicker.adapter

import com.bubblepicker.model.PickerItem

interface BubblePickerAdapter {

    val totalCount: Int

    fun getItem(position: Int): PickerItem

}