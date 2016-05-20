package com.andela.gkuti.mipista;

public class History {
    private String startTime;
    private String endTime;

    public History(String startTime, String endTime) {
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public String getStartTime() {
        return startTime;
    }

    public String getEndTime() {
        return endTime;
    }
}
