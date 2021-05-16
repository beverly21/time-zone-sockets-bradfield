package com.example.timezonezocketsbradfield.server;

import org.springframework.util.ObjectUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

import static java.lang.Thread.sleep;
import static java.time.LocalDateTime.now;

public class TimeZoneServer {
    private ServerSocket serverSocket;
    private Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;

    public TimeZoneServer() {
    }

    public void start(int port) throws IOException {
        serverSocket = new ServerSocket(port);
        clientSocket = serverSocket.accept();
        out = new PrintWriter(clientSocket.getOutputStream(), true);
        in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        readInputAndRespond(in);
    }

    public String readInputAndRespond(BufferedReader in) throws IOException {
        String timeZone = getTimeZone(in);
        if (timeZone.isEmpty()) {
            return now(ZoneId.of("UTC")).format(formatDateTime());
        }
        LocalDateTime localDateTime = now(ZoneId.of(timeZone));
        return localDateTime.format(formatDateTime());
    }

    private String getTimeZone(BufferedReader in) throws IOException {
        int character;

        StringBuilder timeZone = new StringBuilder();

        while (!in.ready()) {
            try {
                sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        while ((character = in.read()) != -1) {
            timeZone.append((char) character);
        }
        return timeZone.toString();
    }


    public static DateTimeFormatter formatDateTime() {
        String timeColonPattern = "HH:mm:ss";
        return DateTimeFormatter.ofPattern(timeColonPattern);
    }

    private void stop(){
        try {
            clientSocket.close();
            in.close();
            out.close();
            serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
