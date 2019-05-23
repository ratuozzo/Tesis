package com.DataAccessLayer.NonAnomaly;

import com.Common.LoggerOps;
import com.DataAccessLayer.Dao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class NonAnomalyDao {
    private final static Logger logger = LoggerFactory.getLogger(NonAnomalyDao.class);

    public boolean create(NonAnomalyBean input) {
        LoggerOps.debug("NonAnomalyDao - create");

        Dao dao = new Dao();
        CallableStatement Sentence;
        boolean output = false;
        try {
            Sentence = dao.getCallableSentence("{Call NonAnomalyCreate (?,?,?)}");
            Sentence.setString(1, input.getIpSource());
            Sentence.setString(2, input.getIpDestination());
            Sentence.setInt(3, input.getPortSource());
            Sentence.setInt(4, input.getPortDestination());
            output = dao.executeCall(Sentence);
            dao.close();

        } catch (Exception e) {
            logger.error( "Method: ", "NonAnomalyDao - create", e.toString() );
        }



        return output;
    }

    public NonAnomalyBean read(int id) {
        LoggerOps.debug("NonAnomalyDao - read");

        Dao dao = new Dao();
        NonAnomalyBean output = null;
        ResultSet rs;
        CallableStatement Sentence = dao.getCallableSentence("{Call GetNonAnomaly (?)}");

        try {
            Sentence.setInt(1, id);

            rs =dao.executeQuery(Sentence);

            if(rs!=null)
                output = getResponseBD(rs);

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("SQL Exception: "+ e.getErrorCode());
        }
        dao.close();

        return output;


    }

    public ArrayList<NonAnomalyBean> readAll(){
        LoggerOps.debug("NonAnomalyDao - readAll");

        Dao dao = new Dao();
        ArrayList<NonAnomalyBean> output = null;
        ResultSet rs;

        CallableStatement Sentence = dao.getCallableSentence("{Call GetAllNonAnomaly ()} ");


        rs =dao.executeQuery(Sentence);

        if(rs!=null)
            output = getResponseArrayListBD(rs);

        dao.close();

        return output;
    }

    public boolean update(NonAnomalyBean input) {

        LoggerOps.debug("NonAnomalyDao - update");

        Dao dao = new Dao();
        CallableStatement Sentence;
        boolean output = false;
        try {
            Sentence = dao.getCallableSentence("{Call NonAnomalyUpdate (?,?,?,?)}");
            Sentence.setString(1, input.getIpSource());
            Sentence.setString(2, input.getIpDestination());
            Sentence.setInt(3, input.getPortSource());
            Sentence.setInt(4, input.getPortDestination());
            Sentence.setInt(5, input.getId());
            output = dao.executeCall(Sentence);
            dao.close();

        } catch (Exception e) {
            logger.error( "Method: ", "NonAnomalyDao - Update", e.toString() );
        }

        return output;
    }


    public boolean delete(int id) {
        LoggerOps.debug("NonAnomalyDao - delete");

        Dao dao = new Dao();
        boolean output;
        CallableStatement Sentence = dao.getCallableSentence("{Call DeleteUser (?)}");


        try {
            Sentence.setInt(1, id);
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("SQL Exception: "+ e.getErrorCode());
        }

        output = dao.executeCall(Sentence);

        dao.close();

        return output;
    }


    private ArrayList<NonAnomalyBean> getResponseArrayListBD(ResultSet rs){
        LoggerOps.debug("NonAnomalyDao - getResponseArrayListBD");

        ArrayList<NonAnomalyBean> output = new ArrayList<NonAnomalyBean>();

        try {
            while (rs.next()){
                NonAnomalyBean aux = new NonAnomalyBean(
                        rs.getInt("id"),
                        rs.getString("IpSource"),
                        rs.getString("IpDestination"),
                        rs.getInt("PortSource"),
                        rs.getInt("PortDestination"));
                output.add(aux);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("SQL Exception: "+ e.getErrorCode());
        }

        return output;

    }

    private NonAnomalyBean getResponseBD(ResultSet rs) throws NullPointerException, SQLException{
        LoggerOps.debug("NonAnomalyDao - getResponseBD");

        NonAnomalyBean output = null;

        while (rs.next()){
            output = new NonAnomalyBean(
                    rs.getInt("id"),
                    rs.getString("IpSource"),
                    rs.getString("IpDestination"),
                    rs.getInt("PortSource"),
                    rs.getInt("PortDestination"));
        }

        return output;

    }
}
