package com.DataAccessLayer;


import com.DataAccessLayer.NonAnomaly.NonAnomalyDao;

public class DaoFactory {

    public static NonAnomalyDao instantiateNonAnomalyDao(){
        return new NonAnomalyDao();
    }
}