package com.example.hoavo.kotlin.api

import com.example.hoavo.kotlin.models.Item

/**
 *  Copyright © 2017 AsianTech inc.
 * Created by at-hoavo on 15/08/2017.
 * Create interface to handel after search list video
 */
interface OnAfterGetApi {
    fun onPerform(items: List<Item>)
}