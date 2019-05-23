package com.DataAccessLayer;

import com.Common.LoggerOps;
import com.Common.Registry;

import java.sql.*;

public class Dao
{
    private Connection _conn;

    private void  connect()
    {
        try{

            _conn = DriverManager.getConnection( Registry.BD_URL, Registry.BD_USER, Registry.BD_PASSWORD );

        }
        catch ( SQLException | NullPointerException e ){
            LoggerOps.error( "Method: Dao - Connect -> " + e.toString() );
        }
    }


    public void close(){
        try{
            _conn.close();
        }catch ( SQLException e ){
            LoggerOps.error( "Method: Dao - Close -> " + e.toString() );
        }
    }

    public CallableStatement getCallableSentence (String statementDefinition){
        CallableStatement Sentence = null;
        try {
            connect();
            Sentence = _conn.prepareCall(statementDefinition);
        } catch (Exception e){
            LoggerOps.error( "Method: Dao - GetCallableSentence -> " + e.toString() );
        }
        return Sentence;
    }

    public ResultSet executeQuery(CallableStatement statement) {
        ResultSet res;
        try {
            res = statement.executeQuery();
            return res;
        }catch (SQLException e){
            LoggerOps.error( "Method: Dao - Execute Query -> " + e.toString() );
            return null;
        }
    }

    public boolean executeCall(CallableStatement statement) {
        try {
            statement.execute();
            return true;
        }catch (SQLException e){
            LoggerOps.error( "Method: Dao - Execute Call -> " + e.toString() );
            return false;
        }
    }


}