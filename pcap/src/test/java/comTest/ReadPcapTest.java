package comTest;

import com.DomainLogicLayer.CommandFactory;
import com.DomainLogicLayer.ReadPcap;
import com.Registry;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ReadPcapTest {

    static String _filePath;

    @BeforeAll
    static void setUp() {
       _filePath = Registry.PCAPFILEPATH+"telnet/telnet-slcl-c.pcap";
    }

    @Test
    void readPcap() {

        ReadPcap command = (ReadPcap) CommandFactory.instantiateReadPcap(_filePath);
        command.execute();

        assertNotNull(command._output);
        assertEquals(180,command._output.size());
    }
}