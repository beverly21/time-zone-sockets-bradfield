package server;

import com.example.timezonezocketsbradfield.server.TimeZoneServer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.util.ReflectionTestUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.zone.ZoneRulesException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;

public class TimeZoneServerTest {
    @Mock
    private BufferedReader in;

    @InjectMocks
    TimeZoneServer timeZoneServer;

    @BeforeEach
    void setUp(){
        openMocks(this);
    }

    @Test
    void readInputAndRespondTest_whenTimeZone_isUTC() throws IOException {
        when(in.readLine()).thenReturn("UTC");
        ReflectionTestUtils.setField(timeZoneServer,"in", in);
        String utcDateTime = timeZoneServer.readInputAndRespond(in);
        assertEquals(utcDateTime, LocalDateTime.now(ZoneId.of("UTC")).format(TimeZoneServer.formatDateTime()));
    }

    @Test
    void readInputAndRespondTest_whenTimeZone_isAmericaLA() throws IOException {
        when(in.readLine()).thenReturn("America/Los_Angeles");
        ReflectionTestUtils.setField(timeZoneServer,"in", in);
        String americaLosAngelesDateTime = timeZoneServer.readInputAndRespond(in);
        assertEquals(americaLosAngelesDateTime, LocalDateTime.now(ZoneId.of("America/Los_Angeles")).format(TimeZoneServer.formatDateTime()));
    }

    @Test
    void readInputAndRespondTest_whenTimeZone_isInvalid_throwsException() throws IOException {
        when(in.readLine()).thenReturn("test");
        ReflectionTestUtils.setField(timeZoneServer,"in", in);
        assertThrows(ZoneRulesException.class, () -> timeZoneServer.readInputAndRespond(in));
    }

    @Test
    void readInputAndRespondTest_whenTimeZone_isEmpty_returnTimeInUTC() throws IOException {
        when(in.readLine()).thenReturn("");
        ReflectionTestUtils.setField(timeZoneServer,"in", in);
        String utcDateTime = timeZoneServer.readInputAndRespond(in);
        assertEquals(utcDateTime, LocalDateTime.now(ZoneId.of("UTC")).format(TimeZoneServer.formatDateTime()));
    }
}
