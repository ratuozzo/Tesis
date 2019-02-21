package com.DomainLogicLayer.Filters;

import com.Common.Entity.Connections.Connection;
import com.DomainLogicLayer.Command;

import java.util.ArrayList;

public abstract class Filter extends Command {

    protected ArrayList<Connection> _input;
    protected ArrayList<ArrayList<Connection>> _output;

    public Filter(ArrayList<Connection> input) {
        _input = input;
        _output = new ArrayList<>();

    }

    @Override
    public void execute() {

        for (Connection connection: _input) {
            if (!add(connection)) {
                ArrayList<Connection> aux = new ArrayList<>();
                aux.add(connection);
                _output.add(aux);
            }
        }
    }

    public ArrayList<ArrayList<Connection>> getOutput() {
        return _output;
    }


    protected boolean add(Connection connection){

        for (ArrayList<Connection> connections : _output) {
            if (matches(connection,connections)) {
                connections.add(connection);
                return true;
            }
        }

        return false;
    }

    protected abstract boolean matches(Connection connection,ArrayList<Connection> connections);
}
