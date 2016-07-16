/**
 * 
 */
package com.iot.data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author htiruvee
 *
 */
public class Datum  implements Serializable{

    
	int sensorId;
    String location;
    Date timestamp;
    double temperature;
    char tempUnit;
    public int getSensorId() {
        return sensorId;
    }
    public void setSensorId(int sensorId) {
        this.sensorId = sensorId;
    }
    public String getLocation() {
        return location;
    }
    public void setLocation(String location) {
        this.location = location;
    }
    public Date getTimestamp() {
        return timestamp;
    }
    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }
    public double getTemperature() {
        return temperature;
    }
    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }
    public char getTempUnit() {
        return tempUnit;
    }
    public void setTempUnit(char tempUnit) {
        this.tempUnit = tempUnit;
    }
    public Datum(int sensorId, String location, Date timestamp, double temperature, char tempUnit) {
        super();
        this.sensorId = sensorId;
        this.location = location;
        this.timestamp = timestamp;
        this.temperature = temperature;
        this.tempUnit = tempUnit;
    }
    public Datum(){
        super();
    }

    @Override
    public String toString(){
        return "SensorId:"+sensorId+", location:"+location+",timestamp: "+timestamp+", temperature:"+ temperature+" "+tempUnit;
    }
}
