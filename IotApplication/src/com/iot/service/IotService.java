/**
 * 
 */
package com.iot.service;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import com.iot.data.Datum;
import com.iot.data.Neuron;

/**
 * @author htiruvee
 *
 */
public class IotService {

    /**
     * @param args
     */
    public static void main(String[] args) {
        serializeDatum();
        List<Datum> datumList = deserializeDatum();
        System.out.println("Data from Sensors: "+datumList);
        if (datumList.size() > 0) {
            List<Neuron> neurons = getLeafNeurons(datumList);
            while (neurons.size() > 1) {
                serializeNeuron(neurons);
                neurons = deserializeNeuron();
                System.out.println("Neurons: "+neurons);
                neurons = getParentNeurons(neurons);
            }

            if (neurons.size() == 0) {
                System.out.println("Final Min and Max = " + datumList.get(0).getTemperature()+" Celcius");
            }
            else {
                System.out.println("Final Min = " + neurons.get(0).getMin() + " Celcius, Max = "
                        + neurons.get(0).getMax()+" Celcius");
            }
        }
        else {
            System.out.println("No data from sensors");
        }
    }

    public static void serializeDatum() {

        List<Datum> datumList = new ArrayList<Datum>();
        Datum datum = null;
        Random r = new Random();
        int low = 0;
        int high = 100;
        double result = 0;
        int tempUnitResult = 0;
        //Generating Sample Datums and adding to datumList
        for (int i = 0; i < 2; i++) {
            result = r.nextInt(high - low) + low;
            tempUnitResult = r.nextInt(2);
            datum = new Datum(i, "location" + i, new Date(), result, tempUnitResult == 0 ? 'C'
                    : 'F');
            datumList.add(datum);
        }
        try {
            FileOutputStream fout = new FileOutputStream("c:\\Users/haripriya/sampleInput.ser");
            ObjectOutputStream oos = new ObjectOutputStream(fout);
            oos.writeObject(datumList);
            oos.close();
            System.out.println("Done");

        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static List<Datum> deserializeDatum() {
        List<Datum> datumList = new ArrayList<Datum>();
        try {
            FileInputStream fin = new FileInputStream("c:\\Users/haripriya/sampleInput.ser");
            ObjectInputStream ois = new ObjectInputStream(fin);
            datumList = (List<Datum>) ois.readObject();
            ois.close();
            return datumList;
        }
        catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public static List<Neuron> deserializeNeuron() {
        List<Neuron> neuronList = new ArrayList<Neuron>();
        try {
            FileInputStream fin = new FileInputStream("c:\\Users/haripriya/sampleInput.ser");
            ObjectInputStream ois = new ObjectInputStream(fin);
            neuronList = (List<Neuron>) ois.readObject();
            ois.close();
            return neuronList;
        }
        catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public static void serializeNeuron(List<Neuron> neurons) {
        try {
            FileOutputStream fout = new FileOutputStream("c:\\Users/haripriya/sampleInput.ser");
            ObjectOutputStream oos = new ObjectOutputStream(fout);
            oos.writeObject(neurons);
            oos.close();
            System.out.println("Done");
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static List<Neuron> getLeafNeurons(List<Datum> datumList) {
        List<Neuron> neuronsList = new ArrayList<Neuron>();
        Datum datum1 = null;
        Datum datum2 = null;
        for (int i = 0; i < datumList.size(); i += 2) {
            datum1 = datumList.get(i);
            generalizeTempUnits(datum1);
            if(i+1 < datumList.size()) {
                datum2 = datumList.get(i + 1);
                generalizeTempUnits(datum2);
            } else {
                datum2 = null;
            }
            neuronsList.add(getNeuron(datum1, datum2));
        }
        return neuronsList;
    }

    private static void generalizeTempUnits(Datum datum) {
        if (datum.getTempUnit() == 'F')
            datum.setTemperature(convertToCelsius(datum.getTemperature()));
    }

    private static Neuron getNeuron(Datum datum, Datum datum2) {
        Neuron n = new Neuron();
        if (datum2 == null) {
            n.setMin(datum.getTemperature());
            n.setMax(datum.getTemperature());
        }
        else {
            if (datum.getTemperature() < datum2.getTemperature()) {
                n.setMin(datum.getTemperature());
                n.setMax(datum2.getTemperature());
            }
            else {
                n.setMin(datum2.getTemperature());
                n.setMax(datum.getTemperature());
            }
        }
        n.setLeft(null);
        n.setRight(null);
        return n;
    }

    public static List<Neuron> getParentNeurons(List<Neuron> childNeurons) {
        List<Neuron> parentNeurons = new ArrayList<Neuron>();
        for (int i = 0; i < childNeurons.size() - 1; i += 2) {
            parentNeurons.add(getParentNeuron(childNeurons.get(i), childNeurons.get(i + 1)));
        }
        return parentNeurons;
    }

    private static Neuron getParentNeuron(Neuron neuron, Neuron neuron2) {
        Neuron n = new Neuron();
        if (neuron.getMax() < neuron2.getMax()) {
            n.setMax(neuron2.getMax());
        }
        else {
            n.setMax(neuron.getMax());
        }

        if (neuron.getMin() < neuron2.getMin()) {
            n.setMin(neuron.getMin());
        }
        else {
            n.setMin(neuron2.getMin());
        }
        n.setLeft(neuron);
        n.setRight(neuron2);
        return n;
    }

    public static double convertToCelsius(double fahrenheit) {
        double celsius = (fahrenheit - 32) * 5 / 9;
        return celsius;
    }

}
