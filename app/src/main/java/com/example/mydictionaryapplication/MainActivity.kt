package com.example.mydictionaryapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.content.Intent
import android.widget.Button
import android.widget.LinearLayout
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mydictionaryapplication.databinding.ActivityMainBinding
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    lateinit var adapter: MeaningAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.searchBtn.setOnClickListener{
            val word = binding.searchInput.text.toString()
            getMeaning(word)
        }
        val backToMenuButton = findViewById<Button>(R.id.button_back_to_menu)
        backToMenuButton.setOnClickListener {
            val intent = Intent(this, MainMenuActivity::class.java)
            startActivity(intent)
            finish()
        }

        adapter = MeaningAdapter(emptyList())
        binding.meaningRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.meaningRecyclerView.adapter = adapter
    }

    private fun getMeaning(word : String){
        setInProgress(true)
        GlobalScope.launch {
            val response = RetrofitInstance.dictionaryApi.getMeaning(word)
            runOnUiThread{
                setInProgress(false)
                response.body()?.first()?.let{
                    setUI(it)
                }
            }
        }
    }

    private fun setUI(response : WordResult){
        binding.wordText.text = response.word
        binding.phoneticText.text = response.phonetic
        adapter.updateNewData(response.meanings)
    }
    private fun setInProgress(inProgress : Boolean){
        if(inProgress){
            binding.searchBtn.visibility = View.INVISIBLE
            binding.progressBar.visibility = View.VISIBLE
        }
        else{
            binding.searchBtn.visibility = View.VISIBLE
            binding.progressBar.visibility = View.INVISIBLE
        }
    }
}