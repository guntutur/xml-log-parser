package com.ssi.uim.logparser.service;

import com.ssi.uim.logparser.model.ParkingLotModel;
import com.ssi.uim.logparser.repository.ParkingLotRepository;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by zer0, the Maverick Hunter
 * on 17/03/19.
 * Class: ProcessParkingLotCommand.java
 */
public class ProcessParkingLotCommand implements Serializable {

    private ParkingLotRepository repository = new ParkingLotRepository();
    private List<ParkingLotModel> modelList = new ArrayList<>();

    public String processCommand(String command) {
        String systemReponse = "";
        String[] fullCommand = command.split(" ");

        switch (fullCommand[0]) {
            case "create_parking_lot" :

                try {
                    systemReponse = initParkingLot(Integer.parseInt(fullCommand[1]));
                } catch (Exception e) {
                    System.out.println("Command expected number, got error : " + e.getMessage());
                }

                break;
            case "leave" :
                try {
                    systemReponse = vehicleLeave(Integer.parseInt(fullCommand[1]));
                } catch (Exception e) {
                    System.out.println("Command expected number, got error : " + e.getMessage());
                }
                break;
            case "park" :
                systemReponse = parkVehicle(fullCommand[1], fullCommand[2]);
                break;
            case "status" :
                systemReponse = status();
                break;
            case "registration_numbers_for_cars_with_colour" :
                systemReponse = getRegistrationForVehicleColour(fullCommand[1]);
                break;
            case "slot_numbers_for_cars_with_colour" :
                systemReponse = getSlotsForVehicleColour(fullCommand[1]);
                break;
            case "slot_number_for_registration_number" :
                systemReponse = getSlotForVehicleRegistration(fullCommand[1]);
                break;
        }

        return systemReponse;
    }

    private String initParkingLot(Integer parkingLotNumber) {
        repository.initParkingLot(parkingLotNumber);
        return "Created a parking lot with " + parkingLotNumber + " slots";
    }

    private String vehicleLeave(Integer slotNumber) {
        repository.vehicleLeave(slotNumber);
        return "Slot number " + slotNumber + " is free";
    }

    private String parkVehicle(String registration, String colour) {

        if (repository.getParkingLotNumber() == null) {
            return "Parking lot is not yet created!";
        } else {

            if (repository.getParkingLotModels().size() == repository.getParkingLotNumber()) {
                return "Sorry, parking lot is full";
            } else {
                Integer allocatedSlot = allocateSlot();
                ParkingLotModel model = new ParkingLotModel(allocatedSlot, registration, colour);
                modelList.add(model);
                repository.setParkingLotModels(modelList);
                return "Allocated slot number: " + allocatedSlot;
            }
        }
    }

    private String getRegistrationForVehicleColour(String vehicleColour) {
        List<String> parkingLotModels = repository.getRegistrationForVehicleColour(vehicleColour);
        return parkingLotModels
                        .stream()
                        .map(Object::toString)
                        .collect(Collectors.joining(", "));
    }

    private String status() {
        StringBuilder statusResult = new StringBuilder();

        statusResult.append("Slot No.    Registration No    Colour\n");
        for (ParkingLotModel model : repository.getParkingLotModels()) {
            statusResult.append(model.getSlot()).append("\t").append(model.getRegistration()).append("\t").append(model.getVehicleColour()).append("\n");
        }

        return statusResult.toString();
    }

    public String getSlotsForVehicleColour(String vehicleColour) {
        List<Integer> parkingLotModels = repository.getSlotsForVehicleColour(vehicleColour);
        return parkingLotModels
                        .stream()
                        .map(Object::toString)
                        .collect(Collectors.joining(", "));
    }

    public String getSlotForVehicleRegistration(String registration) {
        return repository.getSlotForVehicleRegistration(registration);
    }

    private Integer allocateSlot() {

        int allocatedSlot;
        if (repository.getParkingLotModels() == null) {
            allocatedSlot = 1;
        } else {
            int[] slotTaken = new int[repository.getParkingLotModels().size()];
            int index = 0;
            for (ParkingLotModel model : repository.getParkingLotModels()) {
                slotTaken[index] = model.getSlot();
                index++;
            }
            allocatedSlot = findUnallocatedSlotNearEntry(slotTaken, slotTaken.length);
        }

        return allocatedSlot;
    }

    private static int findUnallocatedSlotNearEntry(int[] arr, int n) {

        int val;
        int nextval;

        for (int i = 0; i < n; i++) {

            if (arr[i] <= 0 || arr[i] > n) continue;

            val = arr[i];

            while (arr[val - 1] != val) {
                nextval = arr[val - 1];
                arr[val - 1] = val;
                val = nextval;
                if (val <= 0 || val > n) break;
            }
        }


        for (int i = 0; i < n; i++) {
            if (arr[i] != i + 1) return i + 1;
        }

        return n + 1;
    }
}
