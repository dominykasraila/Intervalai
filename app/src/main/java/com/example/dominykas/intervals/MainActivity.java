package com.example.dominykas.intervals;

import android.app.Activity;
import android.content.Context;
import android.media.AudioManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CompoundButton;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.SeekBar.OnSeekBarChangeListener;


public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    IntervalTrainer intervalTrainer;
    SineWaveGenerator  sineWaveGenerator;
    TextView textView;
    Spinner spinnerTop;
    Spinner spinnerBottom;
    Switch switch1;
    Boolean playAnswer = false;
    private SeekBar volumeSeekbar = null;
    private AudioManager audioManager = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setVolumeControlStream(AudioManager.STREAM_MUSIC);
        double referenceFrequency = 220;
        initControls();

        intervalTrainer = new IntervalTrainer();
        sineWaveGenerator = new SineWaveGenerator(referenceFrequency);
        textView = findViewById(R.id.text);
        spinnerTop = findViewById(R.id.spinnerTop);
        spinnerTop.setSelection(10);
        spinnerTop.setOnItemSelectedListener(this);
        spinnerBottom = findViewById(R.id.spinnerBottom);
        spinnerBottom.setSelection(9);
        spinnerBottom.setOnItemSelectedListener(this);

        switch1 = findViewById(R.id.switch1);
        switch1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    playAnswer = true;
                } else {
                    playAnswer = false;
                }
            }
        });
    }

    public void next(View view) {
        if (playAnswer) playEnd(view);
        textView.setText(intervalTrainer.nextNote());
    }

    public void playInterval(View view) {
        sineWaveGenerator.playMelodic(intervalTrainer.prev_offset, intervalTrainer.offset, 1);
    }

    public void playStart(View view) {
        sineWaveGenerator.play(intervalTrainer.prev_offset, 1);
    }

    public void playEnd(View view) {
        sineWaveGenerator.play(intervalTrainer.offset, 1);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // On selecting a spinner item
        String interval = parent.getItemAtPosition(position).toString();
        if (parent.getId() == R.id.spinnerTop) {
            intervalTrainer.setTopNote(interval);
        }
        if (parent.getId() == R.id.spinnerBottom) {
            intervalTrainer.setBottomNote(interval);
        }
    }
    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub
    }

    private void initControls() {
        try {
            volumeSeekbar = (SeekBar) findViewById(R.id.seekBar1);
            audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
            volumeSeekbar.setMax(audioManager
                    .getStreamMaxVolume(AudioManager.STREAM_MUSIC));
            volumeSeekbar.setProgress(audioManager
                    .getStreamVolume(AudioManager.STREAM_MUSIC));


            volumeSeekbar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
                @Override
                public void onStopTrackingTouch(SeekBar arg0) {
                }

                @Override
                public void onStartTrackingTouch(SeekBar arg0) {
                }

                @Override
                public void onProgressChanged(SeekBar arg0, int progress, boolean arg2) {
                    audioManager.setStreamVolume(AudioManager.STREAM_MUSIC,
                            progress, 0);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

