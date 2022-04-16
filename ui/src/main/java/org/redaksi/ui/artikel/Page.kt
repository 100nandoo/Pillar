package org.redaksi.ui.artikel

import androidx.annotation.StringRes
import org.redaksi.data.remote.CategoryId

data class Page(@StringRes val label: Int, @CategoryId val category: Int)
