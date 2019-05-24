package com.DataAccessLayer.NonAnomaly;

import com.DataAccessLayer.DaoFactory;
import com.DomainLogicLayer.Commands.Orchestrate;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.core.annotation.Order;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class NonAnomalyDaoTest {

    static NonAnomalyDao dao = DaoFactory.instantiateNonAnomalyDao();

    @BeforeAll
    static void setUp() {

        ArrayList<NonAnomalyBean> all = dao.readAll();
        for (int i = 0; i < all.size(); i++) {
            dao.delete(all.get(i).getId());
        }
    }

    @Test
    @Order(1)
    void TestingAll(){

        NonAnomalyBean beanToCreate = new NonAnomalyBean("198.167.175.111",
                "174.168.102.222",80,5432);
        dao.create(beanToCreate);

        ArrayList<NonAnomalyBean> all = dao.readAll();
        assertEquals(1,all.size());

        NonAnomalyBean beanToCreate2 = new NonAnomalyBean("198.167.175.112",
                "174.168.102.223",80,5432);
        dao.create(beanToCreate2);

        all = dao.readAll();
        assertEquals(all.size(),2);

        for (int i = 0; i < all.size(); i++) {

            assertEquals(all.get(i).getPortSource(),80);
            assertEquals(dao.read(all.get(i).getId()).getPortSource(),80);

            assertEquals(dao.read(all.get(i).getId()).getPortSource(),80);
            dao.delete(all.get(i).getId());
        }

        all = dao.readAll();
        assertEquals(all.size(),0);


    }

}