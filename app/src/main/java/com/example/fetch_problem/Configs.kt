package com.example.fetch_problem

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

class Configs {

    companion object {
        const val JSON_URL = "https://fetch-hiring.s3.amazonaws.com/hiring.json"

        const val CLOSED_BOX_WIDTH = 200
        const val OPEN_BOX_WIDTH = 260

        val CURVING_SHAPE = RoundedCornerShape(
            topEnd = 8.dp,
            bottomEnd = 8.dp
        )

        val CONTACT_LIST_BG_COLOR_DARK = Color.hsl(288f, .49f, .16f, alpha = 0.9f)
        val CONTACT_LIST_BG_COLOR_LIGHT = Color.hsl(288f, .49f, .16f, alpha = 0.8f)
        val CONTACT_LIST_TEXT_COLOR = Color.hsl(38f, .96f, .55f)
    }
}