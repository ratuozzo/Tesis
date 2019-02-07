package comTest;

import com.Registry;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

class ReadMultiplePcapsTest {

    static ArrayList<String> _filePaths;

    @BeforeAll
    static void setUp() {
        _filePaths.add(Registry.PCAPFILEPATH+"telnet/telnet-slcl-c.pcap");
        _filePaths.add(Registry.PCAPFILEPATH+"telnet/telnet-swcl-c.pcap");

        _filePaths.add(Registry.PCAPFILEPATH+"ssh/ssh-slcl-c.pcap");
        _filePaths.add(Registry.PCAPFILEPATH+"ssh/ssh-swcl-c.pcap");

        _filePaths.add(Registry.PCAPFILEPATH+"ftp/ftp-slcl-c-fz.pcap");
        _filePaths.add(Registry.PCAPFILEPATH+"ftp/ftp-swcl-c-cmd.pcap");

        _filePaths.add(Registry.PCAPFILEPATH+"http/http-slcl-c-c1.pcap");
        _filePaths.add(Registry.PCAPFILEPATH+"http/http-swcl-c-c3.pcap");

        _filePaths.add(Registry.PCAPFILEPATH+"ping/ping-slcl-c.pcap");
        _filePaths.add(Registry.PCAPFILEPATH+"ping/ping-swcl-c.pcap");
    }

    @Test
    void readMultiplePcaps() {

    }

}