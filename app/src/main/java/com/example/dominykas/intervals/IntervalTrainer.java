package com.example.dominykas.intervals;

import java.util.Random;

class IntervalTrainer {
    private static final String INTERVAL[] = {"u", "m2", "d2", "m3", "d3", "g4", "t", "g5", "m6", "d6", "m7", "d7", "g8"};
    private static int MAX_INTERVAL = indexOf(INTERVAL, "d7");
    private static int bottomNote = -indexOf(INTERVAL, "d6");
    private static int topNote = indexOf(INTERVAL, "m7");

    public int offset = 0;
    public int prev_offset = 0;
    private int interval = 0;

    private static int indexOf(String[] ar, String needle) {
        int index = 0;
        for (String e : ar) {
            if (e.equals(needle)) {
                return index;
            }
            index++;
        }
        return -1;
    }

    public String nextNote() {
        prev_offset = offset;
        interval = 0;
        int max_down = bottomNote - offset;
        int rand_down = max_down < -MAX_INTERVAL ? -MAX_INTERVAL : max_down;
        int max_up = topNote - offset;
        int rand_up = max_up > MAX_INTERVAL ? MAX_INTERVAL : max_up;
        while (interval == 0) {
            Random r = new Random();
            interval = r.nextInt(rand_up - rand_down + 1) + rand_down;
        }
        offset += interval;
        String sign = interval >= 0 ? "^" : "v";
        String sign2 = offset >= 0 ? "+" : "-";
        String line = "%s %2s";
        return String.format(line, sign, INTERVAL[Math.abs(interval)]);
    }

    public void setTopNote(String interval) {
        topNote = indexOf(INTERVAL, interval);
        offset = 0;
    }

    public void setBottomNote(String interval) {
        bottomNote = -indexOf(INTERVAL, interval);
        offset = 0;
    }
}