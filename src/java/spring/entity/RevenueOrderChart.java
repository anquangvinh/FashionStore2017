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
public class RevenueOrderChart {

    private String label;
    private float value;

    public RevenueOrderChart() {
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public float getValue() {
        return value;
    }

    public void setValue(float value) {
        this.value = value;
    }

    public static class RevenueOrderChartComparator implements Comparator<RevenueOrderChart> {

        @Override
        public int compare(RevenueOrderChart o1, RevenueOrderChart o2) {
            float change1 = o1.getValue();
            float change2 = o2.getValue();
            if (change1 < change2) {
                return -1;
            }
            if (change1 > change2) {
                return 1;
            }
            return 0;
        }

    }
}
