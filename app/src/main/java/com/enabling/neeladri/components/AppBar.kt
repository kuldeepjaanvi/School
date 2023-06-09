package com.enabling.neeladri.components

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.enabling.neeladri.R
@Composable
fun AppTopBar(
    name: String,
    showRandom: Boolean,
    onShowRandomDashboardChange: (showRandom: Boolean) -> Unit = { }
) {
    TopAppBar(
        title = { Text(text = name) },
        backgroundColor = Color.Cyan,
        actions = {
           //openPhoneDialer("9886787008")
        }
    )
}

@Composable
private fun RandomDashboard(
    showRandom: Boolean,
    onRandomDashboardChange: (isRandom: Boolean) -> Unit = { }
) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        PrimaryText {
            Text(
                modifier = Modifier,
                text = stringResource(id = R.string.random_dashboard),
                style = MaterialTheme.typography.caption
            )
        }
        Spacer(modifier = Modifier.width(5.dp))
        Checkbox(
            checked = showRandom,
            onCheckedChange = onRandomDashboardChange,
            colors = CheckboxDefaults.colors(
                checkedColor = MaterialTheme.colors.primary,
            )
        )
    }
}

private fun openPhoneDialer(phoneNo: String) {
    val intent = Intent(Intent.ACTION_DIAL)
    intent.data = Uri.parse("tel:$phoneNo")
    //startActivity(intent)
}