package com.example.mysong

import android.annotation.SuppressLint
import android.content.Intent
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.view.View
import android.widget.SeekBar
import kotlinx.android.synthetic.main.activity_main.*
import java.io.BufferedReader
import java.io.FileInputStream
import java.io.InputStreamReader
import java.lang.StringBuilder

class MainActivity : AppCompatActivity() {
    private lateinit var player: MediaPlayer
    private var totalTime: Int=0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        player=MediaPlayer.create(this, R.raw.song)
        player.isLooping=true
       // player.setVolume(0.8f, 0.8f)
        totalTime=player.duration

        show.setOnClickListener {
            val filename="lyrics.txt"
            var fileInputStream:FileInputStream?=null
            fileInputStream=openFileInput(filename)
            var inputStreamReader:InputStreamReader= InputStreamReader(fileInputStream)
            val bufferedReader:BufferedReader= BufferedReader(inputStreamReader)
            val stringBuilder:StringBuilder= StringBuilder()
            var text:String?=null
            while({text=bufferedReader.readLine();text}()!=null){
                stringBuilder.append(text).append("\n")
            }
            lyrics.setText(stringBuilder.toString()).toString()
        }
        add.setOnClickListener {
          val intent = Intent(this, AddLyricsActivity::class.java)
            startActivity(intent)
        }


        musicprogress.max=totalTime
        musicprogress.setOnSeekBarChangeListener(
            object :SeekBar.OnSeekBarChangeListener{
                override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                    if(fromUser){
                        player.seekTo(progress)
                    }
                }

                override fun onStartTrackingTouch(seekBar: SeekBar?) {

                }

                override fun onStopTrackingTouch(seekBar: SeekBar?) {

                }

            }
        )
        Thread(Runnable {
            while(player!=null){
                try{
                    var msg=Message()
                    msg.what=player.currentPosition
                    handler.sendMessage(msg)
                    Thread.sleep(1000)
                }catch (e:InterruptedException){

                }
            }
        }).start()
    }
    @SuppressLint("HandlerLeak")
    var handler=object :Handler(){
        override fun handleMessage(msg: Message) {
            var currentPosition=msg.what
            musicprogress.progress=currentPosition
            var time=createTimeLabel(currentPosition)
            time1.text=time
            var remeintime=createTimeLabel(totalTime-currentPosition)
            time2.text="-$remeintime"
        }
    }

    fun createTimeLabel(time:Int):String{
        var timeLabel=""
        var min=time/1000/60
        var sec=time/1000%60
        timeLabel="$min:"
        if(sec<10) timeLabel+="0"
        timeLabel+=sec
        return timeLabel
    }

    fun playsongClick(v:View){
        if(player.isPlaying){
            player.pause()
            playsong.setBackgroundResource(R.drawable.play_arrow)
        }
        else{
          player.start()
            playsong.setBackgroundResource(R.drawable.pause)
        }
    }

}