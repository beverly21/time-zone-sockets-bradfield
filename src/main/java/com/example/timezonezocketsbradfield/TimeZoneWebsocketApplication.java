package com.example.timezonezocketsbradfield;

import com.example.timezonezocketsbradfield.server.TimeZoneServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;

@SpringBootApplication
public class TimeZoneWebsocketApplication {

	public static void main(String[] args) {

		SpringApplication.run(TimeZoneWebsocketApplication.class, args);
		TimeZoneServer server = new TimeZoneServer();
		try {
			server.start(8091);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
