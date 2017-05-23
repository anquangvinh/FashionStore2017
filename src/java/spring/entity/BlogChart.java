/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spring.entity;

import java.util.Comparator;

/**
 *
 * @author Phan
 */
public class BlogChart {
    private String label;
    private int value;

    public BlogChart() {
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
    
    public static class BlogChartComparator implements Comparator<BlogChart>{

        @Override
        public int compare(BlogChart o1, BlogChart o2) {
            return o1.value - o2.value;
        }
        
    }
}
