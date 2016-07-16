/**
 * 
 */
package com.iot.data;

import java.io.Serializable;

/**
 * @author htiruvee
 *
 */
public class Neuron implements Serializable{

    
	double min;
    double max;
    Neuron left;
    Neuron right;
    public double getMin() {
        return min;
    }
    public void setMin(double min) {
        this.min = min;
    }
    public double getMax() {
        return max;
    }
    public void setMax(double max) {
        this.max = max;
    }
    public Neuron getLeft() {
        return left;
    }
    public void setLeft(Neuron left) {
        this.left = left;
    }
    public Neuron getRight() {
        return right;
    }
    public void setRight(Neuron right) {
        this.right = right;
    }
    public Neuron(double min, double max, Neuron left, Neuron right) {
        super();
        this.min = min;
        this.max = max;
        this.left = left;
        this.right = right;
    }

    public Neuron(){
        super();
    }
   
    public String toString(){
        return "Min:"+min+", Max:"+max+", Left:"+left+", Right:"+right;
    }
}
