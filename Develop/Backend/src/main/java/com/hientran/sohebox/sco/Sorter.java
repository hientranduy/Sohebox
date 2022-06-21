package com.hientran.sohebox.sco;

import java.io.Serializable;

/**
 * Sorter
 * 
 * @author hientran
 */
public class Sorter implements Serializable {

    private static final long serialVersionUID = 1L;

    private String property;

    private String direction;

    public Sorter() {
        super();
    }

    /**
     * Explanation of processing
     *
     * @param property
     * @param direction
     */
    public Sorter(String property, String direction) {
        super();
        this.property = property;
        this.direction = direction;
    }

    public String getProperty() {
        return property;
    }

    public void setProperty(String property) {
        this.property = property;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    @Override
    public String toString() {
        return "Sorter [property=" + property + ", direction=" + direction + "]";
    }

}
