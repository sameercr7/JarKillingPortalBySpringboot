package com.sam.jarstatusportal.Controller;


import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(path = "/data")
public class DataController {

    private final Map<String, String> processMap = new HashMap<>();

    @PostMapping("/executeJar")
    public String executeJar(@RequestParam String jarPath) {
        try {
            File jarFile = new File(jarPath);
            String directory = jarFile.getParent();

            // Path to the log file in the same directory as the JAR file
            String logFilePath = Paths.get(directory, "log.txt").toString();
            // Command to open a new CMD window and execute the JAR file
            String command = String.format("cmd.exe /c start cmd.exe /k \"java -jar \"%s\"\"", jarPath);

            // ProcessBuilder to execute the command
            ProcessBuilder processBuilder = new ProcessBuilder("cmd.exe", "/c", "start", "cmd.exe", "/k", command);
            processBuilder.inheritIO(); // Inherit I/O for real-time output

            // Start the process
            Process process = processBuilder.start();
            process.waitFor(); // Wait for the process to complete

            return "Execution started in a new CMD window";
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return "Execution failed";
        }
    }

    @PostMapping("/killJar")
    public String killJar(@RequestParam String jarPath) {
        try {
            // Format the command to search for the JAR by its filename
            String command = String.format(
                    "for /f \"tokens=1\" %%a in ('jps -l ^| findstr /i \"%s\"') do taskkill /F /PID %%a",
                    jarPath
            );

            // Use ProcessBuilder to open CMD and execute the command
            ProcessBuilder processBuilder = new ProcessBuilder("cmd.exe", "/c", "start", "cmd.exe", "/k", command);

            // Inherit the I/O to see the output in real-time
            processBuilder.inheritIO();

            // Start the process
            Process process = processBuilder.start();

            // Wait for the process to complete
            process.waitFor();

            return "Kill command executed successfully.";
        } catch (Exception e) {
            e.printStackTrace();
            return "Error occurred: " + e.getMessage();
        }
    }




//    @PostMapping("/executeJar")
//    public String executeJar(@RequestParam String jarPath) {
//        try {
//            // Path to your batch file
//            String batchFilePath = "C:\\path\\to\\your\\runJar.bat";
//
//            // Command to open a new CMD window and execute the batch file
//            ProcessBuilder processBuilder = new ProcessBuilder(
//                    "cmd.exe", "/c", "start", "cmd.exe", "/k", batchFilePath + " \"" + jarPath + "\""
//            );
//
//            // Start the process
//            Process process = processBuilder.start();
//            process.waitFor(); // Wait for the process to complete
//
//            return "Execution started in a new CMD window";
//        } catch (IOException | InterruptedException e) {
//            e.printStackTrace();
//            return "Execution failed";
//        }
//    }
}
