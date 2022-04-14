package org.redaksi.pillar

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

data class NavBarItem(@StringRes val label: Int, @DrawableRes val icon: Int, val route: String)
