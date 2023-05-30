package com.example.asmtracebility;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;

public class Data {
    @FXML
    private  final SimpleStringProperty panelId;
    private final SimpleStringProperty pcbId;
    private final SimpleStringProperty panelName;
    private final SimpleStringProperty shopOrder;
    private final SimpleStringProperty componentPN;
    private final SimpleStringProperty refDesignator;
    private final SimpleStringProperty reelId;
    private final SimpleStringProperty tableId;
    private final SimpleStringProperty track;
    private final SimpleStringProperty div;
    private final SimpleStringProperty tower;
    private final SimpleStringProperty level;
    private final SimpleStringProperty originalQuantity;
    private final SimpleStringProperty lotCode;
    private final SimpleStringProperty dateCode;
    private final SimpleStringProperty supplier;
    private final SimpleStringProperty station;
    private final SimpleStringProperty msdLevel;
    private final SimpleStringProperty program;
    private final SimpleStringProperty beginDate;
    private final SimpleStringProperty endDate;


    public Data(String panelId, String pcbId, String panelName, String shopOrder, String componentPN,
                String refDesignator, String reelId, String tableId, String track, String div,
                String tower, String level, String originalQuantity, String lotCode, String dateCode,
                String supplier, String station, String msdLevel, String program, String beginDate,
                String endDate) {
        this.panelId = new SimpleStringProperty(panelId);
        this.pcbId = new SimpleStringProperty(pcbId);
        this.panelName = new SimpleStringProperty(panelName);
        this.shopOrder = new SimpleStringProperty(shopOrder);
        this.componentPN = new SimpleStringProperty(componentPN);
        this.refDesignator = new SimpleStringProperty(refDesignator);
        this.reelId = new SimpleStringProperty(reelId);
        this.tableId = new SimpleStringProperty(tableId);
        this.track = new SimpleStringProperty(track);
        this.div = new SimpleStringProperty(div);
        this.tower = new SimpleStringProperty(tower);
        this.level = new SimpleStringProperty(level);
        this.originalQuantity = new SimpleStringProperty(originalQuantity);
        this.lotCode = new SimpleStringProperty(lotCode);
        this.dateCode = new SimpleStringProperty(dateCode);
        this.supplier = new SimpleStringProperty(supplier);
        this.station = new SimpleStringProperty(station);
        this.msdLevel = new SimpleStringProperty(msdLevel);
        this.program = new SimpleStringProperty(program);
        this.beginDate = new SimpleStringProperty(beginDate);
        this.endDate = new SimpleStringProperty(endDate);
    }

    public Data(String panelId, String pcbId) {
        this.panelId = new SimpleStringProperty(panelId);
        this.pcbId = new SimpleStringProperty(pcbId);
        this.panelName = new SimpleStringProperty("");
        this.shopOrder = new SimpleStringProperty("");
        this.componentPN = new SimpleStringProperty("");
        this.refDesignator = new SimpleStringProperty("");
        this.reelId = new SimpleStringProperty("");
        this.tableId = new SimpleStringProperty("");
        this.track = new SimpleStringProperty("");
        this.div = new SimpleStringProperty("");
        this.tower = new SimpleStringProperty("");
        this.level = new SimpleStringProperty("");
        this.originalQuantity = new SimpleStringProperty("");
        this.lotCode = new SimpleStringProperty("");
        this.dateCode = new SimpleStringProperty("");
        this.supplier = new SimpleStringProperty("");
        this.station = new SimpleStringProperty("");
        this.msdLevel = new SimpleStringProperty("");
        this.program = new SimpleStringProperty("");
        this.beginDate = new SimpleStringProperty("");
        this.endDate = new SimpleStringProperty("");
    }


    public String getPanelId() {
        return panelId.get();
    }

    public String getPcbId() {
        return pcbId.get();
    }

    public String getPanelName() {
        return panelName.get();
    }

    public String getShopOrder() {
        return shopOrder.get();
    }

    public String getComponentPN() {
        return componentPN.get();
    }

    public String getRefDesignator() {
        return refDesignator.get();
    }

    public String getReelId() {
        return reelId.get();
    }

    public String getTableId() {
        return tableId.get();
    }

    public String getTrack() {
        return track.get();
    }

    public String getDiv() {
        return div.get();
    }

    public String getTower() {
        return tower.get();
    }

    public String getLevel() {
        return level.get();
    }

    public String getOriginalQuantity() {
        return originalQuantity.get();
    }

    public String getLotCode() {
        return lotCode.get();
    }

    public String getDateCode() {
        return dateCode.get();
    }

    public String getSupplier() {
        return supplier.get();
    }

    public String getStation() {
        return station.get();
    }

    public String getMsdLevel() {
        return msdLevel.get();
    }

    public String getProgram() {
        return program.get();
    }

    public String getBeginDate() {
        return beginDate.get();
    }

    public String getEndDate() {
        return endDate.get();
    }
    public void setPcbId(String pcbId){
        this.pcbId.set(pcbId);
    }


}
