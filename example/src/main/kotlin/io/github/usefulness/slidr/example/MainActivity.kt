package io.github.usefulness.slidr.example

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import io.github.usefulness.slidr.example.databinding.ActivityMainBinding
import io.github.usefulness.slidr.example.model.AndroidOs
import okio.buffer
import okio.source

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)

        val adapter = OSVersionAdapter(
            onClick = {
                val startIntent = Intent(this@MainActivity, ViewerActivity::class.java).apply {
                    putExtra(ViewerActivity.EXTRA_OS, it)
                }
                startActivity(startIntent)
            },
        )
        adapter.submitList(data)
        binding.recycler.adapter = adapter
        binding.recycler.layoutManager = LinearLayoutManager(this)
    }

    private val data by lazy {
        val moshi = Moshi.Builder().build()
        val listMyData = Types.newParameterizedType(List::class.java, AndroidOs::class.java)
        val jsonAdapter = moshi.adapter<List<AndroidOs>>(listMyData)

        resources.openRawResource(R.raw.android_versions)
            .use { jsonAdapter.fromJson(it.source().buffer()) }
    }
}
