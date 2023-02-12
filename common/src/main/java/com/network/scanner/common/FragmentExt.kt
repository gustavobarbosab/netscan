package com.network.scanner.common

import androidx.fragment.app.Fragment
import com.network.scanner.common.widget.Toolbar
import com.network.scanner.common.widget.ToolbarOwnerListener

fun Fragment.netScanToolbar(): Toolbar = (requireActivity() as ToolbarOwnerListener).toolbar