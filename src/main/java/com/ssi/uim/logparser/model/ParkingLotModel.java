package com.ssi.uim.logparser.model;

import java.io.Serializable;

/**
 * Created by zer0, the Maverick Hunter
 * on 17/03/19.
 * Class: ParkingLotModel.java
 */
public class ParkingLotModel implements Serializable {

    private int slot;
    private String registration;
    private String vehicleColour;

    public ParkingLotModel(int slot, String registration, String vehicleColour) {
        this.slot = slot;
        this.registration = registration;
        this.vehicleColour = vehicleColour;
    }

    public int getSlot() {
        return slot;
    }

    public void setSlot(int slot) {
        this.slot = slot;
    }

    public String getRegistration() {
        return registration;
    }

    public void setRegistration(String registration) {
        this.registration = registration;
    }

    public String getVehicleColour() {
        return vehicleColour;
    }

    public void setVehicleColour(String vehicleColour) {
        this.vehicleColour = vehicleColour;
    }
}
