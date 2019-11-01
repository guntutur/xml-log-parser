package com.ssi.uim.logparser.main;

import com.ssi.uim.logparser.service.ProcessParkingLotCommand;
import com.ssi.uim.logparser.util.ParkingLotUtility;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Stream;

/**
 * Created by zer0, the Maverick Hunter
 * on 16/03/19.
 * Class: App.java
 */
public class App {

    private ParkingLotUtility utility = new ParkingLotUtility();
    private ProcessParkingLotCommand parkingLotCommand = new ProcessParkingLotCommand();

    public static void main(String[] args) throws IOException {

        App app = new App();

        app.processFileInput();
    }

    private void processFileInput() throws IOException {

        String forwarded = "Forwarded";
        String stored = "Stored";
        String error = "error";

        List<String> dir = new ArrayList<>();
        dir.add("osm");

        for (String s : dir) {

            Stream<Path> paths = Files.walk(Paths.get("src/main/resources/" + s));
            FileWriter out = new FileWriter("src/main/resources/error_log_general_" + s + ".txt", true);

            for (Path path : (Iterable<Path>) () -> paths.filter(Files::isRegularFile).iterator()) {
                Scanner in;
                try {

                    in = new Scanner(new InputStreamReader(new FileInputStream(path.toFile()), StandardCharsets.UTF_8));

                    int forwardOccurrences = 0;
                    int storedOccurrences = 0;
                    while (in.hasNextLine()) {
                        String line = in.nextLine();
                        if (!line.startsWith("###")) {
                            System.out.println(prettyPrintXML(sanitizeXml(line), s, path.toFile()));
                            out.write(prettyPrintXML(sanitizeXml(line), s, path.toFile())+"\n");
                            forwardOccurrences++;
                        }
                    }

//                    System.out.println(path.getFileName() + "," + forwardOccurrences + "," + storedOccurrences);

                    in.close();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }

//            System.out.println("MS Log dir "+s+" finished processed");
        }
    }

    private static String sanitizeXml(String input) {

        return input
                .replace("(?<=>)(\\s+)(?=<)", "")
                .replace("####", "")
                .replace("> <>", "")
                .replace("&gt;", ">")
                .replace("&lt;", "<")
                .replace("&quot;", "\"").trim();
    }

    public static String prettyPrintXML(String input, String dir, File file) {
        int indent = 2;
        try {
            Source xmlInput = new StreamSource(new StringReader(input));
            StringWriter stringWriter = new StringWriter();
            StreamResult xmlOutput = new StreamResult(stringWriter);
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            transformerFactory.setAttribute("indent-number", indent);
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.transform(xmlInput, xmlOutput);
            return xmlOutput.getWriter().toString();
        } catch (Exception e) {
            System.out.println(dir);
            System.out.println(file);
            System.out.println(input);
            throw new RuntimeException(e); // simple exception handling, please review it
        }
    }

    private void interactiveShell() {
        // System.out.println("Please enter command : (enter help to see available command with usage)");

        String theInput;
        Scanner scanner = new Scanner(System.in);

        while (true) {

            theInput = scanner.nextLine();

            switch (theInput) {
                case "help" :
                    utility.showHelp();
                    break;
                case "exit" :
                    utility.exitProgram();
                    break;
                default:
                    System.out.println(processCommand(theInput));
            }
        }
    }

    public String processCommand(String command) {
        String response = "";
        if (utility.validateCommand(command)) {
            response = parkingLotCommand.processCommand(command);
        }

        return response;
    }
}
