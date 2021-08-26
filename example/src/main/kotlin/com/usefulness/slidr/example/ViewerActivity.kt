package com.usefulness.slidr.example

import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade
import com.r0adkll.slidr.attachSlidr
import com.r0adkll.slidr.model.SlidrConfig
import com.r0adkll.slidr.model.SlidrPosition
import com.usefulness.slidr.example.databinding.ActivityViewerBinding
import com.usefulness.slidr.example.model.AndroidOs
import kotlin.random.Random

class ViewerActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityViewerBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)

        // Get the status bar colors to interpolate between
        val primary = ContextCompat.getColor(this, R.color.primaryDark)
        val secondary = ContextCompat.getColor(this, R.color.red_500)

        // Build the slidr config
        val numPositions = SlidrPosition.values().size
        val position = SlidrPosition.values()[Random(System.currentTimeMillis()).nextInt(numPositions)]
        binding.position.text = position.name
        val config = SlidrConfig(
            colorPrimary = primary,
            colorSecondary = secondary,
            position = position,
            velocityThreshold = 2400f,
            touchSize = resources.getDimension(R.dimen.touch_size),
        )

        // Attach the Slidr Mechanism to this activity
        attachSlidr(config = config)
        setSupportActionBar(binding.toolbar)
        supportActionBar?.title = ""
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val os = intent.getSerializableExtra(EXTRA_OS) as AndroidOs
        // Set layout contents
        binding.title.text = os.name
        binding.description.text = os.description
        binding.date.text = os.year.toString()
        binding.version.text = os.version
        binding.sdk.text = os.sdkInt.toString()

        // Load header image
        Glide.with(this)
            .load(os.imageUrl)
            .transition(withCrossFade())
            .into(binding.cover)

        listOf(
            binding.color1,
            binding.color2,
            binding.color3,
            binding.color4,
            binding.color5,
        ).forEach { colorView ->
            colorView.setOnClickListener {
                val color = (it.background as ColorDrawable).color
                window.statusBarColor = color
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    companion object {
        const val EXTRA_OS = "extra_os_version"
    }
}
