package com.brunycharotte.mastermindfx;

public class HistoriqueMancheSaver {

    int[] savePTS = new int[10];
    String[] saveWinners = new String[10];

    public void setWinnerPts(int manche, String winner, int pts) {
        manche--;
        savePTS[manche] = pts;
        saveWinners[manche] = winner;
    }
}
