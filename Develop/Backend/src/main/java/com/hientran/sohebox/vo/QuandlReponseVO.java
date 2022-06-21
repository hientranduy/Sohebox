package com.hientran.sohebox.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * @author hientran
 */
@JsonInclude(Include.NON_NULL)
public class QuandlReponseVO extends BaseVO {

    private static final long serialVersionUID = 1L;

    private QuandlDatasetVO dataset;

    /**
     * Constructor
     *
     */
    public QuandlReponseVO() {
        super();
    }

    /**
     * Get dataset
     *
     * @return dataset
     */
    public QuandlDatasetVO getDataset() {
        return dataset;
    }

    /**
     * Set dataset
     *
     * @param dataset
     *            the dataset to set
     */
    public void setDataset(QuandlDatasetVO dataset) {
        this.dataset = dataset;
    }

    /**
     *
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "QuandlReponseVO [dataset=" + dataset + "]";
    }

}