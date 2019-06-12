package com.DomainLogicLayer.Filters;

import com.Common.Entity.Connections.Connection;

import java.util.ArrayList;

public class FilterByDstIp extends Filter {

    public FilterByDstIp(ArrayList<Connection> input) {
        super(input);
    }

    protected boolean matches(Connection connection, ArrayList<Connection> connections) {
        return connections.get(0).getDstIp().equals(connection.getDstIp());
    }

}
