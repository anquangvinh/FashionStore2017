/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spring.entity;

import java.util.Comparator;

/**
 *
 * @author NganNgo
 */
public class QuantityOrderChart {
    private String label;
    private int value;

    public QuantityOrderChart() {
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
    
    public static class QuantityOrderChartComparator implements Comparator<QuantityOrderChart>{

        @Override
        public int compare(QuantityOrderChart o1, QuantityOrderChart o2) {
            return o1.value-o2.value;
        }
        
    }
}
