package com.ssi.uim.logparser.util;

import java.io.Serializable;
import java.util.Arrays;

/**
 * Created by zer0, the Maverick Hunter
 * on 17/03/19.
 * Class: ParkingLotUtility.java
 */
public class ParkingLotUtility implements Serializable {

    private final String[] RECOGNIZED_COMMAND = {
            "create_parking_lot",
            "leave",
            "park",
            "registration_numbers_for_cars_with_colour",
            "status",
            "slot_numbers_for_cars_with_colour",
            "slot_number_for_registration_number"
    };

    public boolean validateCommand(String command) {

        boolean valid = true;

        String ERROR_HINT = "Command {cause}, type help for available command with usage";
        String MISSING_ARGUMENT = "missing one or more argument";
        String NOT_RECOGNIZED = "not recognized";

        String[] fullCommand = command.split(" ");

        switch (fullCommand[0]) {
            case "create_parking_lot" :
            case "leave" :
            case "registration_numbers_for_cars_with_colour" :
            case "slot_numbers_for_cars_with_colour" :
            case "slot_number_for_registration_number" :
                if (fullCommand.length == 1) {
                    System.out.println(ERROR_HINT.replace("{cause}", MISSING_ARGUMENT));
                    valid = false;
                }
                break;
            case "park" :
                if (fullCommand.length < 3) {
                    System.out.println(ERROR_HINT.replace("{cause}", MISSING_ARGUMENT));
                    valid = false;
                }
                break;
        }

        if (Arrays.stream(RECOGNIZED_COMMAND).noneMatch(fullCommand[0]::equals)) {
            System.out.println(ERROR_HINT.replace("{cause}", NOT_RECOGNIZED));
            valid = false;
        }

        return valid;
    }

    public void exitProgram() {
        System.exit(1);
    }

    public void showHelp() {
        String help = "GO-JEK Parking Lot Help, available command : \n";
        help += String.join(", ", RECOGNIZED_COMMAND);
        help += "\n";
        help += "Usage : \n";
        help += "create_parking_lot, takes 1 argument, int of slot assigned\n";
        help += "leave, takes 1 argument, int of slot released\n";
        help += "park, takes 2 argument, string of registration number, string of car colour\n";
        help += "registration_numbers_for_cars_with_colour, takes 1 argument, string of car colour\n";
        help += "status, takes no argument\n";
        help += "slot_numbers_for_cars_with_colour, takes 1 argument, string of car colour\n";
        help += "slot_number_for_registration_number, takes 1 argument, string of number registration\n";

        System.out.println(help);
    }

}
