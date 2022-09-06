package com.usefulness.slidr.example

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SmallTopAppBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController

@Preview
@Composable
private fun DetailsPreview() {
    DetailsView(rememberNavController(), 15)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun DetailsView(navController: NavHostController, sdkInt: Int) {
    val resources = LocalContext.current.resources
    val item = remember { loadData(resources).first { it.sdkInt == sdkInt } }

    Scaffold(
        topBar = { SmallTopAppBar(title = { Text(text = item.name.orEmpty()) }) },
        content = { padding ->
            val scrollState = rememberScrollState()
            Column(
                modifier = Modifier
                    .padding(padding)
                    .padding(horizontal = 16.dp)
                    .verticalScroll(scrollState),
            ) {
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = item.name.orEmpty(),
                    style = MaterialTheme.typography.titleMedium,
                )
                Spacer(modifier = Modifier.height(2.dp))
                if (item.description != null) {
                    Text(
                        text = item.description,
                        style = MaterialTheme.typography.bodySmall.copy(lineHeight = 20.sp),
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = item.description,
                        style = MaterialTheme.typography.bodyMedium.copy(lineHeight = 20.sp),
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = item.description,
                        style = MaterialTheme.typography.bodyLarge.copy(lineHeight = 20.sp),
                    )
                }
                Spacer(modifier = Modifier.height(4.dp))
                Button(onClick = { navController.navigate("details/$sdkInt") }) {
                    Text("Details, Again!")
                }
            }
        },
    )
}
