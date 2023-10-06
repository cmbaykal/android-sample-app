package com.adesso.movee.scene.cinemas.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.KeyboardArrowRight
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.adesso.movee.R

@Composable
fun MapsMarkerDialog(
    state: Boolean,
    title: String,
    subTitle: String,
    webLink: String
) {
    AnimatedVisibility(
        visible = state,
        enter = expandVertically(),
        exit = shrinkVertically(),
    ) {
        Card(
            modifier = Modifier.padding(horizontal = 20.dp, vertical = 18.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color.White
            ),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 8.dp
            ),
            shape = RoundedCornerShape(8.dp),
        ) {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 20.dp, end = 20.dp, top = 10.dp),
                text = title,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 20.dp, end = 20.dp, top = 5.dp),
                text = subTitle,
                fontSize = 15.sp,
                fontWeight = FontWeight.Light
            )
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 20.dp, end = 20.dp, top = 5.dp),
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                color = colorResource(id = R.color.colorPrimary),
                text = webLink,
            )
            Divider(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 10.dp),
                color = colorResource(id = R.color.warm_gray),
                thickness = 1.dp
            )
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(32.dp)
                    .padding(start = 20.dp, end = 20.dp, bottom = 10.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = colorResource(id = R.color.colorPrimary)
                ),
                shape = RoundedCornerShape(11.dp),
                contentPadding = PaddingValues(0.dp),
                onClick = { /*TODO*/ }) {
                Icon(
                    modifier = Modifier.padding(2.dp),
                    imageVector = Icons.Outlined.KeyboardArrowRight,
                    tint = Color.White,
                    contentDescription = null
                )
                Text(
                    modifier = Modifier.wrapContentSize(),
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold,
                    text = "Go!",
                )
            }
        }
    }
}