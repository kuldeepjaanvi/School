package com.enabling.neeladri.components

import androidx.compose.material.ContentAlpha
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.material.LocalContentAlpha as LocalContentAlpha1

@Composable
fun PrimaryText(children: @Composable() () -> Unit) {
    CompositionLocalProvider(LocalContentAlpha1 provides ContentAlpha.high) {
        children.invoke()
    }
}

@Composable
fun SecondaryText(children: @Composable() () -> Unit) {
    CompositionLocalProvider(LocalContentAlpha1 provides ContentAlpha.medium) {
        children.invoke()
    }
}

@Composable
fun DisabledText(children: @Composable() () -> Unit) {
    CompositionLocalProvider(LocalContentAlpha1 provides ContentAlpha.disabled) {
        children.invoke()
    }
}