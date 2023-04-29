package app.notetaking.musicplayer

import android.annotation.SuppressLint
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.view.View
import android.widget.Button
import android.widget.SeekBar
import android.widget.TextView

class MainActivity : AppCompatActivity() {

    private lateinit var mp : MediaPlayer
    private var totalTime: Int = 0
    private lateinit var playBtn: Button
    private lateinit var volumeBar : SeekBar
    private lateinit var positionBar: SeekBar
    private lateinit var elapsedTimeLabel : TextView
    private lateinit var remainingTimeLabel : TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        playBtn = findViewById(R.id.play_button)
        volumeBar = findViewById(R.id.volumeBar)
        positionBar = findViewById(R.id.seek_bar)
        elapsedTimeLabel = findViewById(R.id.elapsedTimeLabel)
        remainingTimeLabel = findViewById(R.id.remainingTimeLabel)

        mp = MediaPlayer.create(this, R.raw.pov)
        mp.isLooping = true
        mp.setVolume(0.5f, 0.5f)
        totalTime = mp.duration

        // Volume Bar
        volumeBar.setOnSeekBarChangeListener(
            object : SeekBar.OnSeekBarChangeListener{
                override fun onProgressChanged(
                    seekBar: SeekBar?,
                    progress: Int,
                    fromUser: Boolean
                ) {
                   if(fromUser){
                       var volumeNum = progress /100.0f
                       mp.setVolume(volumeNum, volumeNum)
                   }
                }

                override fun onStartTrackingTouch(seekBar: SeekBar?) {

                }

                override fun onStopTrackingTouch(seekBar: SeekBar?) {

                }

            }
        )
        //Position Bar
        positionBar.max = totalTime
        positionBar.setOnSeekBarChangeListener(
            object :SeekBar.OnSeekBarChangeListener{
                override fun onProgressChanged(
                    seekBar: SeekBar?,
                    progress: Int,
                    fromUser: Boolean
                ) {
                  if(fromUser){
                      mp.seekTo(progress)
                  }
                }

                override fun onStartTrackingTouch(seekBar: SeekBar?) {

                }

                override fun onStopTrackingTouch(seekBar: SeekBar?) {

                }

            }
        )

        // Thread
        Thread(Runnable {
            while (mp != null){
                try {
                    val msg = Message()
                    msg.what = mp.currentPosition
                    handler.sendMessage(msg)
                    Thread.sleep(1000)

                }catch (_: InterruptedException){
                }
            }
        }).start()
    }

    @SuppressLint("HandlerLeak")
    var handler = object :Handler(){
        override fun handleMessage(msg: Message) {
            val currentPosition = msg.what


            //update positionBar
            positionBar.progress = currentPosition

            //Update Labels
            var elapsedTime = createTimeLabel(currentPosition)
            elapsedTimeLabel.text = elapsedTime

            val remainingTime = createTimeLabel(totalTime - currentPosition)
            remainingTimeLabel.text = remainingTime
        }
    }

    fun createTimeLabel(time : Int): String{
        var timeLabel = ""
        var min = time / 1000 / 60
        var sec = time / 1000 % 60

        timeLabel = "$min:"
        if(sec < 10) timeLabel += "0"
        timeLabel += sec

        return timeLabel
    }


    fun playBtnClick(v: View){

        if(mp.isPlaying){
            // Stop
            mp.pause()
            playBtn.setBackgroundResource(R.drawable.play)
        }else{
            //Start
            mp.start()
            playBtn.setBackgroundResource(R.drawable.pause)
        }

    }
}