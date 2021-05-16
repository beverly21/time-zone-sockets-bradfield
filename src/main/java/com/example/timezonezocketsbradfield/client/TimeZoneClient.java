package com.example.timezonezocketsbradfield.client;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class TimeZoneClient {
    private Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;

    public void startConnection(String ip, int port) throws IOException {
        clientSocket = new Socket(ip, port);
        out = new PrintWriter(clientSocket.getOutputStream(),true);
        in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
    }

    public String getTimeZoneFromServer(String timeZone) throws IOException {
        out.println(timeZone);
        int character;
        StringBuilder timeZoneBuilder = new StringBuilder();

        while (in.ready()) {
            while ((character = in.read()) != -1) {
                timeZoneBuilder.append((char) character);
            }
        }
        return timeZoneBuilder.toString();
    }

    public void stopConnection() {
        try {
            in.close();
            out.close();
            clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
