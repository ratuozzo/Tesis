package com.DomainLogicLayer.Filters;

import com.Common.Entity.Connections.Connection;

import java.util.ArrayList;

public class FilterByProtocol extends Filter {

    public FilterByProtocol(ArrayList<Connection> input) {
        super(input);
    }

    protected boolean matches(Connection connection, ArrayList<Connection> connections) {
        return connections.get(0).getClass() == connection.getClass();
    }

}
