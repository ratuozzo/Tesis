package com.DomainLogicLayer.Commands;

import com.DomainLogicLayer.Helpers.AnomalyDataSetIterator;
import org.deeplearning4j.api.storage.StatsStorage;
import org.deeplearning4j.nn.api.OptimizationAlgorithm;
import org.deeplearning4j.nn.conf.MultiLayerConfiguration;
import org.deeplearning4j.nn.conf.NeuralNetConfiguration;
import org.deeplearning4j.nn.conf.WorkspaceMode;
import org.deeplearning4j.nn.conf.layers.variational.BernoulliReconstructionDistribution;
import org.deeplearning4j.nn.conf.layers.variational.VariationalAutoencoder;
import org.deeplearning4j.nn.multilayer.MultiLayerNetwork;
import org.deeplearning4j.nn.weights.WeightInit;
import org.deeplearning4j.ui.api.UIServer;
import org.deeplearning4j.ui.stats.StatsListener;
import org.deeplearning4j.ui.storage.InMemoryStatsStorage;
import org.nd4j.linalg.activations.Activation;
import org.nd4j.linalg.dataset.api.iterator.DataSetIterator;
import org.nd4j.linalg.dataset.api.preprocessor.NormalizerMinMaxScaler;
import org.nd4j.linalg.dataset.api.preprocessor.NormalizerStandardize;
import org.nd4j.linalg.factory.Nd4j;
import org.nd4j.linalg.io.ClassPathResource;
import org.nd4j.linalg.learning.config.Nesterovs;
import org.nd4j.linalg.learning.config.RmsProp;
import org.nd4j.linalg.learning.config.Sgd;
import org.nd4j.linalg.lossfunctions.LossFunctions;

import java.io.IOException;

public class TrainNeuralNet extends Command {

    private int _batchSize = 12605; //Todo el dataset
    private int _nEpochs = 30000;
    private double _learningRate = 0.00023;
    private MultiLayerNetwork _net;
    private org.deeplearning4j.nn.layers.variational.VariationalAutoencoder _vae;
    private DataSetIterator _trainIter;


    public TrainNeuralNet() {

        try {
            _trainIter = new AnomalyDataSetIterator(new ClassPathResource("NetData/evaluate.csv").getFile().getPath(), _batchSize);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void execute() {

        initializeModel();
        trainModel();
    }

    private void initializeModel() {

        MultiLayerConfiguration conf = new NeuralNetConfiguration.Builder()
                .trainingWorkspaceMode(WorkspaceMode.ENABLED)
                .inferenceWorkspaceMode(WorkspaceMode.ENABLED)
                .updater(new Nesterovs(_learningRate,0.00001))
                .optimizationAlgo(OptimizationAlgorithm.STOCHASTIC_GRADIENT_DESCENT)
                .weightInit(WeightInit.XAVIER)
                .l2(1e-4)
                .list()
                .layer(0, new VariationalAutoencoder.Builder()
                        .activation(Activation.LEAKYRELU)
                        .encoderLayerSizes(6,2)
                        .decoderLayerSizes(2,6)
                        .pzxActivationFunction(Activation.IDENTITY)
                        .reconstructionDistribution(new BernoulliReconstructionDistribution(Activation.SIGMOID.getActivationFunction()))
                        .lossFunction(LossFunctions.LossFunction.MSE)
                        .nIn(10)
                        .nOut(2)
                        .build())
                .pretrain(true).backprop(false).build();

        _net = new MultiLayerNetwork(conf);
        _net.init();

        UIServer uiServer = UIServer.getInstance();
        StatsStorage statsStorage = new InMemoryStatsStorage();
        uiServer.attach(statsStorage);
        _net.setListeners(new StatsListener(statsStorage));

        NormalizerStandardize normalizer = new NormalizerStandardize();
        normalizer.fit(_trainIter);
        _trainIter.setPreProcessor(normalizer);

        _vae = (org.deeplearning4j.nn.layers.variational.VariationalAutoencoder) _net.getLayer(0);
    }

    private void trainModel() {

        for (int i = 0; i < _nEpochs; i++) {
            _net.pretrain(_trainIter);
        }

    }

    public org.deeplearning4j.nn.layers.variational.VariationalAutoencoder getVae(){
        return _vae;
    }
}
