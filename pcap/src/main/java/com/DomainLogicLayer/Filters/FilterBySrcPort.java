package com.DomainLogicLayer.Filters;

import com.Common.Entity.Connections.Connection;

import java.util.ArrayList;

public class FilterBySrcPort extends Filter {

    public FilterBySrcPort(ArrayList<Connection> input) {
        super(input);
    }

    protected boolean matches(Connection connection, ArrayList<Connection> connections) {
        return connections.get(0).getSrcPort() == connection.getSrcPort();
    }

}
