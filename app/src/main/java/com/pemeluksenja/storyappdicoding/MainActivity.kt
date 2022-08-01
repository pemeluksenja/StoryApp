package com.pemeluksenja.storyappdicoding

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.app.ActivityOptionsCompat
import androidx.core.util.Pair
import androidx.recyclerview.widget.LinearLayoutManager
import com.pemeluksenja.storyappdicoding.adapter.LoadingStateAdapter
import com.pemeluksenja.storyappdicoding.adapter.StoryAdapter
import com.pemeluksenja.storyappdicoding.databinding.ActivityMainBinding
import com.pemeluksenja.storyappdicoding.model.ListStoryItem
import com.pemeluksenja.storyappdicoding.viewmodel.TimelineStoryViewModel
import com.pemeluksenja.storyappdicoding.viewmodel.ViewModelFactory

class MainActivity : AppCompatActivity() {
    private lateinit var bind: ActivityMainBinding
    private lateinit var storyAdapter: StoryAdapter
    private val timelineStoryViewModel: TimelineStoryViewModel by viewModels {
        val context = this@MainActivity
        val pref =
            context.getSharedPreferences(R.string.token_pref.toString(), Context.MODE_PRIVATE)
        val token = pref.getString(R.string.token.toString(), "")!!
        ViewModelFactory(this, token)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind = ActivityMainBinding.inflate(layoutInflater)
        setContentView(bind.root)
        showLoadingProcess(true)
        displayStories()
        bind.floatingActionButton.setOnClickListener {
            val intent = Intent(this, AddStoryActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
        }
        supportActionBar?.setTitle(R.string.app_name)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        val intent = Intent(this, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        finish()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflaterMenu = menuInflater
        inflaterMenu.inflate(R.menu.menus, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.app_bar_settings -> {
                val context = this
                val pref = context.getSharedPreferences(
                    R.string.token_pref.toString(),
                    Context.MODE_PRIVATE
                )
                val editor = pref.edit()
                editor.remove(R.string.token.toString())
                editor.remove(getString(R.string.email))
                editor.remove(getString(R.string.password))
                editor.apply()
                startActivity(Intent(this, LoginActivity::class.java))
                finish()
            }
            R.id.app_bar_maps -> {
                startActivity(Intent(this, MapsActivity::class.java))
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun displayStories() {
        showLoadingProcess(false)
        storyAdapter = StoryAdapter()
        bind.storyRV.layoutManager = LinearLayoutManager(this)
        bind.storyRV.adapter = storyAdapter.withLoadStateFooter(
            footer = LoadingStateAdapter {
                storyAdapter.retry()
            }
        )
        timelineStoryViewModel.story.observe(this) { story ->
            storyAdapter.submitData(lifecycle, story)

        }
        storyAdapter.setOnProfileCallback(object : StoryAdapter.OnProfileCallback {
            override fun onProfileClicked(data: ListStoryItem) {
                chosenProfile(data)
            }
        })
    }

    private fun chosenProfile(story: ListStoryItem) {
        val name: TextView = findViewById(R.id.userName)
        val storyPict: ImageView = findViewById(R.id.userImageStory)
        val optionsCompat: ActivityOptionsCompat =
            ActivityOptionsCompat.makeSceneTransitionAnimation(
                this,
                Pair(storyPict, getString(R.string.imageStoryDetail)),
                Pair(name, getString(R.string.nama_pengguna)),
            )
        val sendData = Intent(this, StoryDetailActivity::class.java)
        sendData.putExtra(StoryDetailActivity.EXTRA_STORY, story)
        startActivity(sendData, optionsCompat.toBundle())
        Toast.makeText(this, "Memuat Story " + story.name, Toast.LENGTH_SHORT).show()
    }

    private fun showLoadingProcess(isLoading: Boolean) {
        if (isLoading) {
            bind.loading.visibility = View.VISIBLE
        } else {
            bind.loading.visibility = View.GONE
        }
    }
}