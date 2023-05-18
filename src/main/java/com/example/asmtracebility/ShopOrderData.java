package com.example.asmtracebility;

import javafx.beans.property.SimpleStringProperty;
public class ShopOrderData extends Data {
    private final SimpleStringProperty panelBarcode;
    private final SimpleStringProperty boardBarcode;

    public ShopOrderData(String PanelBarcode, String Boardbarcode) {
        super(null, null, null, null, null, null, null, null, null, null, null, null, null);
        this.panelBarcode = new SimpleStringProperty(PanelBarcode);
        this.boardBarcode = new SimpleStringProperty(Boardbarcode);
    }

    public String getPanelBarcode() {
        return panelBarcode.get();
    }

    public String getBoardBarcode() {
        return boardBarcode.get();
    }
}
