package com.hientran.sohebox.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;

/**
 * @author hientran
 */
@Entity
@Table(name = "image_tbl", uniqueConstraints = {
        @UniqueConstraint(name = "UQ_image", columnNames = { "captureDate" }) })
public class ImageTbl extends BaseTbl {

    private static final long serialVersionUID = 1L;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "captureDate", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date captureDate;

}
