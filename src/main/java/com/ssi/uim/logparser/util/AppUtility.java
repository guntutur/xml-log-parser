package com.ssi.uim.logparser.util;

import java.io.Serializable;
import java.util.Arrays;

/**
 * Created by zer0, the Maverick Hunter
 * on 17/03/19.
 * Class: AppUtility.java
 */
public class AppUtility implements Serializable {

    public boolean validateCommand(String command) {

        boolean valid = true;

        String ERROR_HINT = "Command {cause}, type help for example";
        String MISSING_ARGUMENT = "missing one or more argument";

        String[] fullCommand = command.split(" ");

        if (fullCommand.length < 3) {
            System.out.println(ERROR_HINT.replace("{cause}", MISSING_ARGUMENT));
            valid = false;
        } else {
            if (!fullCommand[2].equals("MS")) {
                if (!fullCommand[2].equals("JMS")) {
                    valid = false;
                    MISSING_ARGUMENT = "Only MS OR JMS is recognized";
                    System.out.println(ERROR_HINT.replace("{cause}", MISSING_ARGUMENT));
                }
            }
        }

        return valid;
    }

    public void exitProgram() {
        System.exit(1);
    }

    public void showHelp() {
        System.out.println("example : /opt/Oracle/log_jms/AIA/ 3-123123123 JMS");
    }

}
