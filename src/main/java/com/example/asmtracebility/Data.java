package com.example.asmtracebility;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Data {
    private final SimpleStringProperty component;
    private  final SimpleStringProperty panelBarcode;
    private final SimpleStringProperty panelName;
    private final SimpleStringProperty boardBarcode;
    //private final SimpleStringProperty station;
   // private final SimpleStringProperty matrixIndexX;
    //private final SimpleStringProperty matrixIndexY;
    private final SimpleStringProperty refDesignator;
    private final SimpleStringProperty componentBarcode;
    private final SimpleStringProperty batch;
    private final SimpleStringProperty originalQuanity;
    private final SimpleStringProperty packagingUid;
    private final SimpleStringProperty manufactureDate;
   // private final SimpleStringProperty manufacturer;
    private final SimpleStringProperty msdLevel;
    private final SimpleStringProperty serial;
    //private final SimpleStringProperty supplier;
    private final SimpleStringProperty expireDate;


    public Data(String component, String panelBarcode, String panelName, String boardBarcode, String refDesignator, String componentBarcode, String batch, String originalQuanity, String packagingUid, String manufactureDate, String msdLevel, String serial, String expireDate) {
        this.component = new SimpleStringProperty(component);
        this.panelBarcode = new SimpleStringProperty(panelBarcode);
        this.panelName = new SimpleStringProperty(panelName);
        this.boardBarcode = new SimpleStringProperty(boardBarcode);
        //this.station = new SimpleStringProperty(station);
        //this.matrixIndexX = new SimpleStringProperty(matrixIndexX);
        //this.matrixIndexY = new SimpleStringProperty(matrixIndexY);
        this.refDesignator = new SimpleStringProperty(refDesignator);
        this.componentBarcode = new SimpleStringProperty(componentBarcode);
        this.batch = new SimpleStringProperty(batch);
        this.originalQuanity = new SimpleStringProperty(originalQuanity);
        this.packagingUid = new SimpleStringProperty(packagingUid);
        this.manufactureDate = new SimpleStringProperty(manufactureDate);
        //this.manufacturer = new SimpleStringProperty(manufacturer);
        this.msdLevel = new SimpleStringProperty(msdLevel);
        this.serial = new SimpleStringProperty(serial);
        //this.supplier = new SimpleStringProperty(supplier);
        this.expireDate = new SimpleStringProperty(expireDate);

    }

    public Data(String panelBarcode, String boardBarcode){
        this.panelBarcode = new SimpleStringProperty(panelBarcode);
        this.boardBarcode = new SimpleStringProperty(boardBarcode);
        this.component = new SimpleStringProperty("");
        this.panelName = new SimpleStringProperty("");
        this.refDesignator = new SimpleStringProperty("");
        this.componentBarcode = new SimpleStringProperty("");
        this.batch = new SimpleStringProperty("");
        this.originalQuanity = new SimpleStringProperty("");
        this.packagingUid = new SimpleStringProperty("");
        this.manufactureDate = new SimpleStringProperty("");
        this.msdLevel = new SimpleStringProperty("");
        this.serial = new SimpleStringProperty("");
        //this.supplier = new SimpleStringProperty(supplier);
        this.expireDate = new SimpleStringProperty("");
    }

    public String getComponent(){
        return component.get();
    }
    public String getPanel_Barcode(){
        return panelBarcode.get();
    }

    public String getPanel_Name(){
        return panelName.get();
    }
    public String getBoard_Barcode(){
        return boardBarcode.get();
    }
//    public String getStation(){
//        return station.get();
//    }
//    public String getMatrix_Index_X(){
//        return matrixIndexX.get();
//    }
//    public String getMatrix_Index_Y(){
//        return matrixIndexY.get();
//    }
    public String getRef_Designator(){
        return refDesignator.get();
    }
    public String getComponent_Barcode(){
        return componentBarcode.get();
    }
    public String getBatch(){
        return batch.get();
    }
    public String getOriginal_Quanity(){
        return originalQuanity.get();
    }
    public String getPackaging_UID(){
        return packagingUid.get();
    }
    public String getManufacture_Date(){
        return manufactureDate.get();
    }

//    public String getManufacturer(){
//        return manufacturer.get();
//    }
    public String getMSD_Level(){
        return msdLevel.get();
    }
    public String getSerial(){
        return serial.get();
    }
//    public String getSupplier(){
//        return supplier.get();
//    }
    public String getExpire_Date(){
        return expireDate.get();
    }

    public void setBoardBarcode(String boardBarcode){
        this.boardBarcode.set(boardBarcode);
    }


}
