package com.andela.gkuti.mipista;

public enum Constants {
    ACTION("com.andela.gkuti.mipista.ACTIVITY_RECOGNITION_DATA"),
    DATABASE_NAME("MiPista"),
    TABLE_NAME("Tracker"),
    LOCATION_COLUMN("Location"),
    DATE_COLUMN("Date"),
    START_TIME_COLUMN("StartTime"),
    END_TIME_COLUMN("EndTime"),
    DURATION_COLUMN("Duration"),
    ID_COLUMN("_id"),
    DATA_FILENAME("MiPista");
    String value;

    Constants(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
