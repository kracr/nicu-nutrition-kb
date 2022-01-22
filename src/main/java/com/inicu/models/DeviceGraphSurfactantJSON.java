package com.inicu.models;

import java.sql.Timestamp;
import java.util.List;

public class DeviceGraphSurfactantJSON {

    Timestamp entryTime;
    String value;

    public Timestamp getEntryTime() {
        return entryTime;
    }

    public void setEntryTime(Timestamp entryTime) {
        this.entryTime = entryTime;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
