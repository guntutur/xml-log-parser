package com.ssi.uim.logparser.main;

import com.ssi.uim.logparser.service.SearchNeedleInTheHaystack;
import com.ssi.uim.logparser.util.AppUtility;

import java.io.*;
import java.util.Scanner;
//import java.util.stream.Stream;

/**
 * Created by zer0, the Maverick Hunter
 * on 16/03/19.
 * Class: App.java
 */
public class App {

    private AppUtility utility = new AppUtility();
    private SearchNeedleInTheHaystack parkingLotCommand = new SearchNeedleInTheHaystack();
    private static final String MS_LOG_NAME_PREFIX = "OSM_MS";
    private static final String JMS_LOG_NAME_PREFIX = "jms.ms";

    public static void main(String[] args) throws IOException, InterruptedException {
        App app = new App();
        app.interactiveShell();
    }

    private void interactiveShell() throws IOException, InterruptedException {
        System.out.println("Welcome to OSM Log Parser : (type exit to keluar, help to show example)");
        System.out.println("Type in absolute_file_path(full path) string_pattern(ncx-id) kind_of_search(MS/JMS) :");

        String theInput;
        Scanner scanner = new Scanner(System.in);

        while (true) {

            System.out.print("[input]> ");
            theInput = scanner.nextLine();

            switch (theInput) {
                case "help" :
                    utility.showHelp();
                    break;
                case "exit" :
                    utility.exitProgram();
                    break;
                default:
                    processCommand(theInput);
            }
        }
    }

    public void processCommand(String command) throws IOException, InterruptedException {
        if (utility.validateCommand(command)) {
            String[] fullCommand = command.split(" ");
            String fileLookUpPattern = fullCommand[2].equals("MS") ? MS_LOG_NAME_PREFIX : JMS_LOG_NAME_PREFIX;
            parkingLotCommand.processCommand(fullCommand[0], fullCommand[1], fileLookUpPattern);
            System.out.println(" Files Processed, Operation is now completed, list this dir and check the generated file, bye!");
            utility.exitProgram();
        }
    }


//    private void processSingleFileInput(String filePath, String pattern) throws IOException {
//
//        File path = new File(filePath);
//        FileWriter out = new FileWriter("xml_pattern_" + pattern + ".txt", true);
//
//        Scanner in;
//        try {
//
//            in = new Scanner(new InputStreamReader(new FileInputStream(path), StandardCharsets.UTF_8));
//            String previousLine = null;
//            while (in.hasNextLine()) {
//                String line = in.nextLine();
//                if (!line.startsWith("###")) {
//                    if (line.contains(pattern)) {
//                        System.out.println("found pattern, writing..");
//                        assert previousLine != null;
//                        out.write(sanitizeXml(previousLine) + "\n");
//                        out.write(prettyPrintXML(sanitizeXml(line), "forcedArgs", path)+"\n");
//                    }
//                }
//                previousLine = line;
//            }
//
//            in.close();
//            out.close();
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        }
//    }

//    private void processBulkFileInputJava7(String filePath, String pattern, String prefix) throws IOException {
//
//        List<String> dir = new ArrayList<>();
//        dir.add(filePath);
//
//        for (String s : dir) {
//            System.out.println("ENTERING DIRECTORY : " + s);
//            File paths = new File(filePath);
//            FileWriter out = new FileWriter("xml_pattern_" + pattern + ".txt", true);
//
//            for (File file : Objects.requireNonNull(paths.listFiles())) {
//                if (file.getName().startsWith(prefix)) {
//                    System.out.println("CHECKING FILE : " + file.getName());
//                    Scanner in;
//                    try {
//
//                        in = new Scanner(new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8));
//                        String previousLine = null;
//                        while (in.hasNextLine()) {
//                            String line = in.nextLine();
//                            if (!line.startsWith("###")) {
//                                if (line.contains(pattern)) {
//                                    System.out.println("found pattern on "+file.getName()+", writing..");
//                                    assert previousLine != null;
//                                    out.write(sanitizeXml(previousLine) + "\n");
//                                    out.write(prettyPrintXML(sanitizeXml(line), "forcedArgs", file)+"\n");
//                                }
//                            }
//                            previousLine = line;
//                        }
//
//                        in.close();
//                    } catch (FileNotFoundException e) {
//                        e.printStackTrace();
//                    }
//                }
//            }
//            out.close();
//        }
//    }

//    private void processBulkFileInput(String filePath, String pattern) throws IOException {
//
//        List<String> dir = new ArrayList<>();
//        dir.add(filePath);
//
//        for (String s : dir) {
//            System.out.println("ENTERING DIRECTORY : " + s);
//            Stream<Path> paths = Files.walk(Paths.get(filePath));
//            FileWriter out = new FileWriter("xml_pattern_" + pattern + ".txt", true);
//
//            for (Path path : (Iterable<Path>) () -> paths.filter(Files::isRegularFile).iterator()) {
//                System.out.println("CHECKING FILE : " + path.getFileName());
//                Scanner in;
//                try {
//
//                    in = new Scanner(new InputStreamReader(new FileInputStream(path.toFile()), StandardCharsets.UTF_8));
//                    String previousLine = null;
//                    while (in.hasNextLine()) {
//                        String line = in.nextLine();
//                        if (!line.startsWith("###")) {
//                            if (line.contains(pattern)) {
//                                System.out.println("found pattern on "+path.getFileName()+", writing..");
//                                assert previousLine != null;
//                                out.write(sanitizeXml(previousLine) + "\n");
//                                out.write(prettyPrintXML(sanitizeXml(line), "forcedArgs", path.toFile())+"\n");
//                            }
//                        }
//                        previousLine = line;
//                    }
//
//                    in.close();
//                } catch (FileNotFoundException e) {
//                    e.printStackTrace();
//                }
//            }
//            out.close();
//        }
//    }

//    private void processFileInput() throws IOException {
//
//        String forwarded = "Forwarded";
//        String stored = "Stored";
//        String error = "error";
//
//        List<String> dir = new ArrayList<>();
//        dir.add("osm");
//
//        for (String s : dir) {
//
//            Stream<Path> paths = Files.walk(Paths.get("src/main/resources/" + s));
//            FileWriter out = new FileWriter("src/main/resources/error_log_general_" + s + ".txt", true);
//
//            for (Path path : (Iterable<Path>) () -> paths.filter(Files::isRegularFile).iterator()) {
//                Scanner in;
//                try {
//
//                    in = new Scanner(new InputStreamReader(new FileInputStream(path.toFile()), StandardCharsets.UTF_8));
//
//                    int forwardOccurrences = 0;
//                    int storedOccurrences = 0;
//                    while (in.hasNextLine()) {
//                        String line = in.nextLine();
//                        if (!line.startsWith("###")) {
//                            System.out.println(prettyPrintXML(sanitizeXml(line), s, path.toFile()));
//                            out.write(prettyPrintXML(sanitizeXml(line), s, path.toFile())+"\n");
//                            forwardOccurrences++;
//                        }
//                    }
//
////                    System.out.println(path.getFileName() + "," + forwardOccurrences + "," + storedOccurrences);
//
//                    in.close();
//                } catch (FileNotFoundException e) {
//                    e.printStackTrace();
//                }
//            }
//
////            System.out.println("MS Log dir "+s+" finished processed");
//        }
//    }
}
