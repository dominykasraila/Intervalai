package com.example.dominykas.intervals;

import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioTrack;

public class SineWaveGenerator {
    private double referenceFrequency;

    private static final int AMPLITUDE_DIV = 8;
    private static final double SEMITONE_RATIO = Math.pow(2., 1./12);
    private static final int sampleRate = 44100; // Feel free to change this
    private AudioTrack track;


    public SineWaveGenerator(double referenceFrequency) {
        this.referenceFrequency = referenceFrequency;
    }

    public void initTrack(int duration) {
        track = new AudioTrack(AudioManager.STREAM_MUSIC,
                sampleRate,
                AudioFormat.CHANNEL_OUT_DEFAULT,
                AudioFormat.ENCODING_PCM_16BIT,
                sampleRate * duration,
                AudioTrack.MODE_STATIC
        );
    }

    public void play(int offset, int duration) {
        if (track != null) track.release();
        double frequency = referenceFrequency * Math.pow(SEMITONE_RATIO, offset);
        short soundData[] = new short[sampleRate * duration];

        for(int i = 0; i < soundData.length; i++) {
            double sample = 2*(i%(sampleRate/frequency))/(sampleRate/frequency)-1;
            soundData[i] = (short) (sample * Short.MAX_VALUE / AMPLITUDE_DIV);
        }
        initTrack(1);
        track.write(soundData, 0, soundData.length);
        track.play();
    }

    public void playMelodic(int prev_offset, int offset, int duration) {
        if (track != null) track.release();
        double frequency1 = referenceFrequency * Math.pow(SEMITONE_RATIO, prev_offset);
        double frequency2 = referenceFrequency * Math.pow(SEMITONE_RATIO, offset);
        short soundData[] = new short[sampleRate * duration];

        for(int i = 0; i < soundData.length / 2; i++) {
            double sample = 2*(i%(sampleRate/frequency1))/(sampleRate/frequency1)-1;
            soundData[i] = (short) (sample * Short.MAX_VALUE / AMPLITUDE_DIV);
        }

        for(int i = soundData.length / 2;  i < soundData.length; i++) {
            double sample = 2*(i%(sampleRate/frequency2))/(sampleRate/frequency2)-1;
            soundData[i] = (short) (sample * Short.MAX_VALUE / AMPLITUDE_DIV);
        }
        initTrack(4);
        track.write(soundData, 0, soundData.length);
        track.play();
    }
}
