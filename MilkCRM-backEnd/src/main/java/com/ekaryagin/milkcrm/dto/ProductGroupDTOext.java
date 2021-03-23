package com.ekaryagin.milkcrm.dto;

import java.util.List;

public class ProductGroupDTOext {

    private long id;
    private String title;
    private String vendor;
    private List<ManagerDTO> owner;

    private int articleColumn;
    private int countColumn;
    private String sheetName;
    private int addressRow;
    private int addressCell;
    private int dateRow;
    private int dateCell;
    private String titleForFile;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getVendor() {
        return vendor;
    }

    public void setVendor(String vendor) {
        this.vendor = vendor;
    }

    public List<ManagerDTO> getOwner() {
        return owner;
    }

    public void setOwner(List<ManagerDTO> owner) {
        this.owner = owner;
    }

    public int getArticleColumn() {
        return articleColumn;
    }

    public void setArticleColumn(int articleColumn) {
        this.articleColumn = articleColumn;
    }

    public int getCountColumn() {
        return countColumn;
    }

    public void setCountColumn(int countColumn) {
        this.countColumn = countColumn;
    }

    public String getSheetName() {
        return sheetName;
    }

    public void setSheetName(String sheetName) {
        this.sheetName = sheetName;
    }

    public int getAddressRow() {
        return addressRow;
    }

    public void setAddressRow(int addressRow) {
        this.addressRow = addressRow;
    }

    public int getAddressCell() {
        return addressCell;
    }

    public void setAddressCell(int addressCell) {
        this.addressCell = addressCell;
    }

    public int getDateRow() {
        return dateRow;
    }

    public void setDateRow(int dateRow) {
        this.dateRow = dateRow;
    }

    public int getDateCell() {
        return dateCell;
    }

    public void setDateCell(int dateCell) {
        this.dateCell = dateCell;
    }

    public String getTitleForFile() {
        return titleForFile;
    }

    public void setTitleForFile(String titleForFile) {
        this.titleForFile = titleForFile;
    }
}
