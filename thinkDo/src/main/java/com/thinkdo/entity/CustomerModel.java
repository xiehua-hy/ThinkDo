package com.thinkdo.entity;

import java.io.Serializable;

/**
 * Created by Administrator on 2015/5/29.
 */
public class CustomerModel implements Serializable {
    protected String id;
    protected String name;
    protected String tel;
    protected String address;
    protected String date;
    protected String PyIndex;
    protected String company;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPyIndex() {
        return PyIndex;
    }

    public void setPyIndex(String pyIndex) {
        PyIndex = pyIndex;
    }

    public void initEmptyValue() {
        if (id == null) id = "";
        if (name == null) name = "";
        if (tel == null) tel = "";
        if (address == null) address = "";
        if (date == null) date = "";
        if (PyIndex == null) PyIndex = "";
        if (company == null) company = "";
    }
}
