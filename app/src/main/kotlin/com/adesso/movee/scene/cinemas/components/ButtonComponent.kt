package com.adesso.movee.scene.cinemas.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.adesso.movee.R

@Composable
fun ButtonComponent(
    modifier: Modifier = Modifier,
    text: String,
    onClick: () -> Unit
) {
    Button(
        modifier = modifier,
        contentPadding = PaddingValues(horizontal = 20.dp, vertical = 10.dp),
        colors = ButtonDefaults.buttonColors(containerColor = colorResource(id = R.color.colorPrimary)),
        shape = RoundedCornerShape(10.dp),
        onClick = onClick
    ) {
        Text(
            modifier = Modifier.wrapContentSize(),
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold,
            text = text,
        )
    }
}