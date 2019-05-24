package com.DomainLogicLayer.Commands;

import com.Common.Registry;
import com.DataAccessLayer.DaoFactory;
import com.DataAccessLayer.NonAnomaly.NonAnomalyBean;
import com.DataAccessLayer.NonAnomaly.NonAnomalyDao;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class TranslateTest {

    Translate command = (Translate) CommandFactory.instantiateTraslate(Registry.getRESOURCEFILEPATH());

    @BeforeEach
    void setUp() {
        NonAnomalyDao dao = DaoFactory.instantiateNonAnomalyDao();

        NonAnomalyBean beanToCreate = new NonAnomalyBean("198.167.175.111",
                "174.168.102.222",80,5432);
        dao.create(beanToCreate);

        beanToCreate = new NonAnomalyBean("174.168.102.222",
                "198.167.175.111",0,0);
        dao.create(beanToCreate);

    }

    @Test
    void name() {

        command.execute();

        assertNotNull(command);

    }

    @AfterEach
    void tearDown() {
        NonAnomalyDao dao = DaoFactory.instantiateNonAnomalyDao();
        ArrayList<NonAnomalyBean> all = dao.readAll();

        for (int i = 0; i < all.size(); i++) {
            dao.delete(all.get(i).getId());
        }
    }
}