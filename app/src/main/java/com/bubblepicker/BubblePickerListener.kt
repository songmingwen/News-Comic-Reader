package com.bubblepicker

import com.bubblepicker.model.PickerItem

interface BubblePickerListener {

    fun onBubbleSelected(item: PickerItem)

    fun onBubbleDeselected(item: PickerItem)

}