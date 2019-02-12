package com.Common.Entity.Connections.Ftp;

import com.Common.Entity.Connections.FtpConnection;
import com.Common.Entity.Connections.SshConnection;
import com.Common.Entity.Socket;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class FtpConnectionTest {

    FtpConnection fc;


    @BeforeEach
    void setUp() {

        Socket src = new Socket("192.168.1.112",5554);
        Socket dst = new Socket("192.168.1.112",5554);
        fc = new FtpConnection(src, dst);

    }

    @Test
    void testTree() {

        assertTrue(fc.getModelTree().isRoot());
        assertEquals(9, fc.getModelTree().size());
        assertEquals(6, fc.getModelTree().height());

    }


}