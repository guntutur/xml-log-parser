package com.ssi.uim.logparser.repository;

import com.ssi.uim.logparser.model.ParkingLotModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zer0, the Maverick Hunter
 * on 17/03/19.
 * Class: ParkingLotRepository.java
 */
public class ParkingLotRepository {

    private List<ParkingLotModel> parkingLotModels = new ArrayList<>();
    private Integer parkingLotNumber;

    public List<ParkingLotModel> getParkingLotModels() {
        return parkingLotModels;
    }

    public void setParkingLotModels(List<ParkingLotModel> parkingLotModels) {
        this.parkingLotModels = parkingLotModels;
    }

    public void initParkingLot(Integer parkingLotNumber) {
        this.setParkingLotNumber(parkingLotNumber);
    }

    /**
     * index is -1 of the slot
     * */
    public void vehicleLeave(Integer slotNumber) {
        this.getParkingLotModels().remove(this.getParkingLotModels().get(slotNumber-1));
    }

    public List<String> getRegistrationForVehicleColour(String vehicleColour) {
        List<String> tmp = new ArrayList<>();

        this.getParkingLotModels().forEach(parkingLotModel -> {
            if (parkingLotModel.getVehicleColour().equals(vehicleColour)) {
                tmp.add(parkingLotModel.getRegistration());
            }
        });

        return tmp;
    }

    public List<Integer> getSlotsForVehicleColour(String vehicleColour) {
        List<Integer> tmp = new ArrayList<>();

        this.getParkingLotModels().forEach(parkingLotModel -> {
            if (parkingLotModel.getVehicleColour().equals(vehicleColour)) {
                tmp.add(parkingLotModel.getSlot());
            }
        });

        return tmp;
    }

    public String getSlotForVehicleRegistration(String registration) {

        for (ParkingLotModel model : this.getParkingLotModels()) {
            if (model.getRegistration().equals(registration)) return String.valueOf(model.getSlot());
        }

        return "Not found";
    }

    public Integer getParkingLotNumber() {
        return parkingLotNumber;
    }

    public void setParkingLotNumber(Integer parkingLotNumber) {
        this.parkingLotNumber = parkingLotNumber;
    }
}
