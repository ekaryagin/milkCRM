package com.ekaryagin.milkcrm.entity.products;

import com.ekaryagin.milkcrm.entity.employee.Manager;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name="product_group")
public class ProductGroup {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String title;
    private String vendor;

    @OneToMany(fetch= FetchType.EAGER)
    private Set<Manager> owner;

    private String templatePath;
    private int articleColumn;
    private int countColumn;
    private String sheetName;

    private int addressRow;
    private int addressCell;
    private int dateRow;
    private int dateCell;

    private String titleForFile;

    public void addOwner(Manager manager){
        owner.add(manager);
    }

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

    public Set<Manager> getOwner() {
        return owner;
    }

    public void setOwner(Set<Manager> owner) {
        this.owner = owner;
    }

    public String getTemplatePath() {
        return templatePath;
    }

    public void setTemplatePath(String templatePath) {
        this.templatePath = templatePath;
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
