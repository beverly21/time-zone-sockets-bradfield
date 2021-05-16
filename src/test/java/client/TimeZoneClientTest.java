package client;

import com.example.timezonezocketsbradfield.client.TimeZoneClient;
import com.example.timezonezocketsbradfield.server.TimeZoneServer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TimeZoneClientTest {

    @InjectMocks
    TimeZoneClient timeZoneClient;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getTimeZoneTest() throws IOException {
        timeZoneClient.startConnection("127.0.0.1", 8091);
        String response  = timeZoneClient.getTimeZoneFromServer("America/Los_Angeles");
        assertEquals(LocalDateTime.now(ZoneId.of("America/Los_Angeles")).format(TimeZoneServer.formatDateTime()), response);
    }
}
