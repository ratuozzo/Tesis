package com.DomainLogicLayer.Helpers;

import org.datavec.api.writable.Text;
import org.datavec.api.writable.Writable;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.dataset.DataSet;
import org.nd4j.linalg.factory.Nd4j;

import java.io.File;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

/**
 * @author wangfeng
 */
public class AnomalyDataSetReader {
    private int skipNumLines;
    private int skipNumColumns;
    private int longestTimeSequence;
    private int shortest;
    private Iterator<List<Writable>> iter;
    private Path filePath;
    private int totalExamples;
    private Queue<String> currentLines;

    public AnomalyDataSetReader(File file) {
        this.skipNumLines = 0;
        this.skipNumColumns = 0;
        this.longestTimeSequence = 0;
        this.shortest = 14;
        this.filePath = file.toPath();
        this.currentLines =  new LinkedList<String>();
        doInitialize();
    }
    public void doInitialize(){
        List<List<Writable>> dataLines = new ArrayList<>();
        try {
            List<String> lines = Files.readAllLines(filePath, Charset.forName("UTF-8"));
            for (int i = skipNumLines; i < lines.size(); i ++) {
                String tempStr = lines.get(i);
                currentLines.offer(tempStr);
                int templength = tempStr.split(",").length - skipNumColumns;
                longestTimeSequence = longestTimeSequence < templength? templength:longestTimeSequence;
                List<Writable> dataLine = new ArrayList<>();
                String[] wary = tempStr.split(",");
                for (int j = skipNumColumns; j < wary.length; j++ ) {
                    dataLine.add(new Text(wary[j]));
                }
                dataLines.add(dataLine);
            }
        } catch (Exception e) {
            throw new RuntimeException("loading data failed");
        }
        iter = dataLines.iterator();
        totalExamples = dataLines.size();
    }

    public DataSet next(int num) {

        INDArray features = Nd4j.create(new int[]{num, shortest}, 'f');
        INDArray featuresMask = Nd4j.ones(num,shortest);
        for (int i = 0, k = 0; i < num && iter.hasNext(); i ++) {
            List<Writable> line= iter.next();
            int index = 0;
            for (Writable w: line) {
                features.putScalar(new int[]{i, index}, w.toDouble());
                ++index;
            }
            if (line.size() < longestTimeSequence) {// the default alignmentMode is ALIGN_START
                for(int step = line.size(); step < longestTimeSequence; step++) {
                    featuresMask.putScalar(i, step, 0.0D);
                }
            }
        }
        INDArray labels = Nd4j.create(2, 2);
        labels.putScalar(new int[]{0,0},1);
        labels.putScalar(new int[]{0,1},0);
        labels.putScalar(new int[]{1,0},0);
        labels.putScalar(new int[]{1,1},1);
        DataSet dSet = new DataSet(features, labels, featuresMask, featuresMask);
        return dSet;
    }

    public boolean hasNext() {
        return iter != null && iter.hasNext();
    }

    public List<String> getLabels() {
        return null;
    }

    public void reset() {
        doInitialize();
    }
    public int totalExamples() {
        return totalExamples;
    }

    public Queue<String> currentLines() {
        return currentLines;
    }

}
