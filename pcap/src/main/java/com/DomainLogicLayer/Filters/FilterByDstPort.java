package com.DomainLogicLayer.Filters;

import com.Common.Entity.Connections.Connection;

import java.util.ArrayList;

public class FilterByDstPort extends Filter {

    public FilterByDstPort(ArrayList<Connection> input) {
        super(input);
    }

    protected boolean matches(Connection connection, ArrayList<Connection> connections) {
        return connections.get(0).getDstPort() == connection.getDstPort();
    }

}
