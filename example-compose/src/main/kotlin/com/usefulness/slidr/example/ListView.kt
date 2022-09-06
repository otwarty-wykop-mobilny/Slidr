package com.usefulness.slidr.example

import android.content.res.Resources
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SmallTopAppBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.usefulness.slidr.example.model.AndroidOs
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromStream

@Preview
@Composable
private fun Dupa() {
    ListView(rememberNavController())
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun ListView(navController: NavHostController) {
    Scaffold(
        topBar = { SmallTopAppBar(title = { Text("Slidr") }) },
        content = { padding ->
            val resources = LocalContext.current.resources
            val listContent = remember { loadData(resources) }
            val state = rememberSaveable(saver = LazyListState.Saver, key = "dupa") {
                LazyListState(
                    firstVisibleItemIndex = 0,
                    firstVisibleItemScrollOffset = 0
                )
            }
            LazyColumn(
                state=state,
                modifier = Modifier.padding(padding),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                content = {
                    items(listContent) { item ->
                        Column(
                            modifier = Modifier
                                .clickable { navController.navigate("details/${item.sdkInt}") }
                                .padding(horizontal = 16.dp, vertical = 8.dp),
                            content = {
                                Text(
                                    text = item.name.orEmpty(),
                                    style = MaterialTheme.typography.titleMedium,
                                )
                                Spacer(modifier = Modifier.height(2.dp))
                                if (item.description != null) {
                                    Text(
                                        text = item.description,
                                        style = MaterialTheme.typography.bodySmall,
                                    )
                                }
                            },
                        )
                    }
                },
            )
        },
    )
}

@OptIn(ExperimentalSerializationApi::class)
internal fun loadData(resources: Resources) =
    resources.openRawResource(R.raw.android_versions)
        .use { stream -> Json.decodeFromStream(ListSerializer(AndroidOs.serializer()), stream) }
