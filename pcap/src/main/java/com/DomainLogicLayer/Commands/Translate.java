package com.DomainLogicLayer.Commands;

import com.Common.FileOps;
import com.DataAccessLayer.DaoFactory;
import com.DataAccessLayer.NonAnomaly.NonAnomalyBean;
import com.DataAccessLayer.NonAnomaly.NonAnomalyDao;

import java.util.ArrayList;

public class Translate extends Command {

    private String _pathToSave;
    FileOps _ops;

    public Translate(String pathToSave) {
        _pathToSave = pathToSave + "\\whitelist.rules";
        _ops = new FileOps(_pathToSave);
    }

    @Override
    public void execute() {

        NonAnomalyDao nonAnomalyDao = DaoFactory.instantiateNonAnomalyDao();
        ArrayList<NonAnomalyBean> all = nonAnomalyDao.readAll();

        for (int i = 0; i < all.size(); i++) {
            beanToRule(all.get(i));
        }

        _ops.close();

    }

    private void beanToRule(NonAnomalyBean nonAnomalyBean) {

        _ops.write("alert tcp !"+nonAnomalyBean.getIpSource()+" "+nonAnomalyBean.getPortSource());
        _ops.write(" -> "+nonAnomalyBean.getIpDestination()+" "+nonAnomalyBean.getPortDestination());
        _ops.writeLn(" (msg:\" Anomalia Detectada!\")");



    }
}
