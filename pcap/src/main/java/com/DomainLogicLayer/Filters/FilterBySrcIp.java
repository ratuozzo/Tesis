package com.DomainLogicLayer.Filters;

import com.Common.Entity.Connections.Connection;

import java.util.ArrayList;

public class FilterBySrcIp extends Filter {

    public FilterBySrcIp(ArrayList<Connection> input) {
        super(input);
    }

    protected boolean matches(Connection connection, ArrayList<Connection> connections) {
        return connections.get(0).getClass() == connection.getClass();
    }

}
