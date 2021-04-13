package com.example.mysong

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_add_lyrics.*
import kotlinx.android.synthetic.main.activity_add_lyrics.lyrics
import kotlinx.android.synthetic.main.activity_main.*
import java.io.*
import java.lang.Exception
import java.lang.StringBuilder

class AddLyricsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_lyrics)
        val filename="lyrics.txt"
        var fileInputStream: FileInputStream?=null
        fileInputStream=openFileInput(filename)
        var inputStreamReader: InputStreamReader = InputStreamReader(fileInputStream)
        val bufferedReader: BufferedReader = BufferedReader(inputStreamReader)
        val stringBuilder: StringBuilder = StringBuilder()
        var text:String?=null
        while({text=bufferedReader.readLine();text}()!=null){
            stringBuilder.append(text).append("\n")
        }
        lyrics.setText(stringBuilder.toString()).toString()
        save.setOnClickListener {
         val data=lyrics.text.toString()
            val fileOutputStream:FileOutputStream
            try{
            fileOutputStream=openFileOutput("lyrics.txt", Context.MODE_PRIVATE)
            fileOutputStream.write(data.toByteArray())}
            catch (e:Exception){
                e.printStackTrace()
            }
            catch (e:FileNotFoundException){
                e.printStackTrace()
            }
           // val intent = Intent(this, MainActivity::class.java)
           // startActivity(intent)
        }

    }


}