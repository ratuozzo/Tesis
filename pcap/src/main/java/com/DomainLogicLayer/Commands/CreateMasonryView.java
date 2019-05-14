package com.DomainLogicLayer.Commands;

import com.Common.Entity.Connections.Connection;

import java.util.ArrayList;

public class CreateMasonryView extends Command {

    private ArrayList<Connection> _connections;

    CreateMasonryView(ArrayList<Connection> connections) {
        _connections = connections;
    }

    @Override
    public void execute() {

    }

}
