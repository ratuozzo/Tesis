package com.DomainLogicLayer.Commands;

import com.DomainLogicLayer.Helpers.AnomalyDataSetIterator;
import org.deeplearning4j.nn.conf.MultiLayerConfiguration;
import org.deeplearning4j.nn.conf.NeuralNetConfiguration;
import org.deeplearning4j.nn.conf.WorkspaceMode;
import org.deeplearning4j.nn.conf.layers.variational.BernoulliReconstructionDistribution;
import org.deeplearning4j.nn.conf.layers.variational.VariationalAutoencoder;
import org.deeplearning4j.nn.multilayer.MultiLayerNetwork;
import org.deeplearning4j.nn.weights.WeightInit;
import org.nd4j.linalg.activations.Activation;
import org.nd4j.linalg.dataset.api.iterator.DataSetIterator;
import org.nd4j.linalg.dataset.api.preprocessor.NormalizerMinMaxScaler;
import org.nd4j.linalg.factory.Nd4j;
import org.nd4j.linalg.io.ClassPathResource;
import org.nd4j.linalg.learning.config.RmsProp;

import java.io.IOException;

public class TrainNeuralNet extends Command {

    private int _nEpochs = 10;
    private int _rngSeed = 12345;
    private MultiLayerNetwork _net;
    private org.deeplearning4j.nn.layers.variational.VariationalAutoencoder _vae;
    private DataSetIterator _trainIter;


    public TrainNeuralNet() {

        try {
            _trainIter = new AnomalyDataSetIterator(new ClassPathResource("NetData/train.csv").getFile().getPath(), 1);
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

        Nd4j.getRandom().setSeed(_rngSeed);
        MultiLayerConfiguration conf = new NeuralNetConfiguration.Builder()
                .seed(_rngSeed)
                .trainingWorkspaceMode(WorkspaceMode.ENABLED)
                .inferenceWorkspaceMode(WorkspaceMode.ENABLED)
                .updater(new RmsProp(0.0001))
                .weightInit(WeightInit.XAVIER)
                .l2(1e-4)
                .list()
                .layer(0, new VariationalAutoencoder.Builder()
                        .activation(Activation.LEAKYRELU)
                        .encoderLayerSizes(5, 2)
                        .decoderLayerSizes(2, 5)
                        .pzxActivationFunction(Activation.IDENTITY)
                        .reconstructionDistribution(new BernoulliReconstructionDistribution(Activation.SIGMOID.getActivationFunction()))
                        .nIn(10)
                        .nOut(2)
                        .build())
                .pretrain(true).backprop(false).build();

        _net = new MultiLayerNetwork(conf);
        _net.init();

        NormalizerMinMaxScaler normalizer = new NormalizerMinMaxScaler();
        normalizer.fit(_trainIter);
        _trainIter.setPreProcessor(normalizer);

        _vae = (org.deeplearning4j.nn.layers.variational.VariationalAutoencoder) _net.getLayer(0);
    }

    private void trainModel() {

        for (int i = 0; i < _nEpochs; i++) {
            System.out.println(("Epoch: " + i));
            _net.pretrain(_trainIter);
        }

    }

    public org.deeplearning4j.nn.layers.variational.VariationalAutoencoder getVae(){
        return _vae;
    }
}
