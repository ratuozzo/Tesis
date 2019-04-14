package com.DomainLogicLayer.Commands;

import com.DomainLogicLayer.Helpers.AnomalyDataSetIterator;
import org.deeplearning4j.nn.workspace.LayerWorkspaceMgr;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.dataset.api.DataSet;
import org.nd4j.linalg.dataset.api.iterator.DataSetIterator;
import org.nd4j.linalg.io.ClassPathResource;

import java.io.IOException;

public class EvaluateData extends Command {


    private org.deeplearning4j.nn.layers.variational.VariationalAutoencoder _vae;
    private DataSetIterator _testIter;
    private DataSetIterator _trainIter;


    private double minX = 1000000;
    private double maxX = -1000000;
    private double minY = 1000000;
    private double maxY = -1000000;

    private int belongCount = 0;
    private int notBelongCount = 0;

    public EvaluateData(org.deeplearning4j.nn.layers.variational.VariationalAutoencoder vae ) {

        _vae = vae;

        try {
            _trainIter = new AnomalyDataSetIterator(new ClassPathResource("NetData/train.csv").getFile().getPath(), 1);
            _testIter = new AnomalyDataSetIterator(new ClassPathResource("NetData/evaluate.csv").getFile().getPath(), 1);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void execute() {

        calculateArea();

        while (_testIter.hasNext()) {
            DataSet testdata = _testIter.next();
            INDArray testFeatures = testdata.getFeatures();
            INDArray result = _vae.activate(testFeatures, false, LayerWorkspaceMgr.noWorkspaces());
            boolean belongs = belongsTo(result.getDouble(0,0),result.getDouble(0,1));
            //System.out.println("Data: " + testFeatures + " X: " + result.getDouble(0, 0) + " Y: " + result.getDouble(0, 1) + " Belongs: " + belongs);

        }
        System.out.println("Belongs: "+belongCount + " Not Belongs: " + notBelongCount);



    }

    private void calculateArea() {


        while (_trainIter.hasNext()) {
            DataSet testdata = _trainIter.next();
            INDArray testFeatures = testdata.getFeatures();
            INDArray result = _vae.activate(testFeatures, false, LayerWorkspaceMgr.noWorkspaces());

            double x = result.getDouble(0,0);
            double y = result.getDouble(0,1);
            if (x <= minX) {
                minX = x;
            }
            if (y <=minY) {
                minY = y;
            }
            if (x >= maxX) {
                maxX = x;
            }
            if (y >= maxY) {
                maxY = y;
            }
        }
        //System.out.println("MinX: " + minX + " MaxX: " + maxX + " MinY: " + minY + " MaxY: " + maxY );


    }

    private boolean belongsTo(double x, double y) {
        if ((minX < x && maxX > x) &&
                (minY < y && maxY > y)) {

            belongCount++;
            return true;
        }
        notBelongCount++;
        return false;
    }

}
