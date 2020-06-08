package com.ssi.uim.logparser.service;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

/**
 * Created by zer0, the Maverick Hunter
 * on 17/03/19.
 * Class: SearchNeedleInTheHaystack.java
 */
public class SearchNeedleInTheHaystack implements Serializable {

    public void processCommand(String filePath, String pattern, String prefix) throws IOException, InterruptedException {
        List<String> dir = new ArrayList<>();
        dir.add(filePath);

        String anim= "|/-\\";
        int x = 0;
        for (String s : dir) {
            File paths = new File(filePath);
            FileWriter out = new FileWriter("xml_pattern_" + pattern + ".txt", true);

            for (File file : Objects.requireNonNull(paths.listFiles())) {
                if (file.getName().startsWith(prefix)) {
                    Scanner in;
                    try {

                        in = new Scanner(new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8));
                        String previousLine = null;
                        while (in.hasNextLine()) {
                            String line = in.nextLine();
                            if (!line.startsWith("###")) {
                                if (line.contains(pattern)) {
                                    assert previousLine != null;
                                    out.write(sanitizeXml(previousLine) + "\n");
                                    out.write(prettyPrintXML(sanitizeXml(line), "forcedArgs", file)+"\n");
                                }
                            }
                            previousLine = line;
                        }

                        in.close();
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
                String data = "\r" + anim.charAt(x % anim.length()) + " " + x;
                System.out.write(data.getBytes());
                Thread.sleep(100);
                x++;
            }
            out.close();
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
}
