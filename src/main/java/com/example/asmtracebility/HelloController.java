package com.example.asmtracebility;

import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Priority;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


public class HelloController {



    @FXML
    private BorderPane borderPane;
    @FXML
    private AnchorPane myAnchorPane;
    @FXML ScrollPane myScrollPane;
    @FXML
    private TableView<Data> myTableView;
    @FXML
    private TableColumn<Data, String> colComponent;
    @FXML
    private TableColumn<Data, String> colPanelBarcode;
    @FXML
    private TableColumn<Data, String> colPanelName;
    @FXML
    private TableColumn<Data, String> colBoardBarcode;
    @FXML
    private TableColumn<Data, String> colStation;
    @FXML
    private TableColumn<Data, String> colMatrixIndexX;
    @FXML
    private TableColumn<Data, String> colMatrixIndexY;
    @FXML
    private TableColumn<Data, String> colRefDesignator;
    @FXML
    private TableColumn<Data, String> colComponentBarcode;
    @FXML
    private TableColumn<Data, String> colBatch;
    @FXML
    private TableColumn<Data, String> colOriginalQuanity;
    @FXML
    private TableColumn<Data, String> colPackagingUid;
    @FXML
    private TableColumn<Data, String> colManufactureDate;
    @FXML
    private TableColumn<Data, String> colManufacturer;
    @FXML
    private TableColumn<Data, String> colMsdLevel;
    @FXML
    private TableColumn<Data, String> colSerial;
    @FXML
    private TableColumn<Data, String> colSupplier;
    @FXML
    private TableColumn<Data, String> colExpireDate;



    @FXML
    private TextField searchText;

    ObservableList<Data> data = FXCollections.observableArrayList();
    List<String> searchOptions = Arrays.asList("Component", "PanelBarcode", "ShopOrder", "PanelName", "BoardBarcode", "RefDesignator", "ComponentBarcode", "Batch", "OriginalQuantity", "PackagingUID", "ManufactureDate", "MSDLevel", " Serial", "ExpiryDate" );
    @FXML
    private ChoiceBox<String> myChoiceBox;

    @FXML
    private void initialize(){
        borderPane.setCenter(myAnchorPane);
        myChoiceBox.setValue("Component");
        myChoiceBox.setItems(FXCollections.observableArrayList(searchOptions));
        colComponent.setCellValueFactory(new PropertyValueFactory<>("Component"));
        colPanelBarcode.setCellValueFactory(new PropertyValueFactory<>("Panel_Barcode"));
        colPanelName.setCellValueFactory(new PropertyValueFactory<>("Panel_Name"));
        colBoardBarcode.setCellValueFactory(new PropertyValueFactory<>("Board_Barcode"));
//        colStation.setCellValueFactory(new PropertyValueFactory<>("Station"));
//        colMatrixIndexX.setCellValueFactory(new PropertyValueFactory<>("Matrix_Index_X"));
//        colMatrixIndexY.setCellValueFactory(new PropertyValueFactory<>("Matrix_Index_Y"));
        colRefDesignator.setCellValueFactory(new PropertyValueFactory<>("Ref_Designator"));
        colComponentBarcode.setCellValueFactory(new PropertyValueFactory<>("Component_Barcode"));
        colBatch.setCellValueFactory(new PropertyValueFactory<>("Batch"));
        colOriginalQuanity.setCellValueFactory(new PropertyValueFactory<>("Original_Quanity"));
        colPackagingUid.setCellValueFactory(new PropertyValueFactory<>("Packaging_UID"));
        colManufactureDate.setCellValueFactory(new PropertyValueFactory<>("Manufacture_Date"));
//        colManufacturer.setCellValueFactory(new PropertyValueFactory<>("Manufacturer"));
        colMsdLevel.setCellValueFactory(new PropertyValueFactory<>("MSD_Level"));
        colSerial.setCellValueFactory(new PropertyValueFactory<>("Serial"));
//        colSupplier.setCellValueFactory(new PropertyValueFactory<>("Supplier"));
        colExpireDate.setCellValueFactory(new PropertyValueFactory<>("Expire_Date"));

    }

    @FXML
    void btnSearchClicked(ActionEvent event) {
        String selectedValue = myChoiceBox.getValue();
        String searchTextValue = searchText.getText();

        if (searchTextValue.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Empty Search Text");
            alert.setHeaderText(null);
            alert.setContentText("Please enter a valid search text.");
            alert.showAndWait();
            return;
        }

        data.clear();
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(getQuery(selectedValue))){

            boolean dataFound = false;

            if (selectedValue.equals("ShopOrder")) {
                statement.setString(1,searchTextValue);
                // Remove the unnecessary columns
                myTableView.getColumns().removeAll(colComponent, colPanelBarcode, colBoardBarcode, colPanelName, colRefDesignator, colComponentBarcode,
                        colBatch, colOriginalQuanity, colPackagingUid, colManufactureDate, colSerial, colExpireDate,
                        colMsdLevel);

                // Add the necessary columns
                myTableView.getColumns().addAll(colPanelBarcode, colBoardBarcode);

                ResultSet resultSet = statement.executeQuery();

                //ObservableList<Data> dataList = FXCollections.observableArrayList();
                while (resultSet.next()) {
                    //String shopOrder = resultSet.getString("ShopOrder");
                    String panelBarcode = resultSet.getString("Panel_Barcode");
                    String boardBarcode = resultSet.getString("Board_Barcode");

                    System.out.println(panelBarcode + "+" + boardBarcode);
                   // ShopOrderData shopOrderData = new ShopOrderData(panelBarcode, boardBarcode);
                   data.add(new Data(panelBarcode, boardBarcode));

                    dataFound = true;
                }
                myTableView.setItems(data);
            }else {
                myTableView.getColumns().setAll(colComponent, colPanelBarcode, colPanelName, colBoardBarcode,
                        colRefDesignator, colComponentBarcode, colBatch, colOriginalQuanity, colPackagingUid,
                        colManufactureDate, colMsdLevel, colSerial, colExpireDate);

                statement.setString(1,searchTextValue);
                ResultSet resultSet = statement.executeQuery();
                while (resultSet.next()) {
                    String component = resultSet.getString("Component");
                    String panelBarcode = resultSet.getString("Panel_Barcode");
                    String panelName = resultSet.getString("Panel_Name");
                    //String station = resultSet.getString("Station");
                    // String matrixIndexX = resultSet.getString("Matrix_Index_X");
                    //String matrixIndexY = resultSet.getString("Matrix_Index_Y");
                    String boardBarcode = resultSet.getString("Board_Barcode");
                    String refDesignator = resultSet.getString("RefDesignator");
                    String componentBarcode = resultSet.getString("Component_Barcode");
                    String batch = resultSet.getString("Batch");
                    String originalQuanity = resultSet.getString("Original_Quantity");
                    String packagingUid = resultSet.getString("PackagingUniqueId");
                    String manufactureDate = resultSet.getString("Manufacture_Date");
                    //String manufacturer = resultSet.getString("Manufacturer");
                    String msdLevel = resultSet.getString("MSDLevel");
                    String serial = resultSet.getString("Serial");
                    //String supplier = resultSet.getString("Supplier");
                    String expireDate = resultSet.getString("Expiry_Date");

                    dataFound = true;

                    data.add(new Data(component, panelBarcode, panelName, boardBarcode, refDesignator, componentBarcode, batch, originalQuanity, packagingUid, manufactureDate, msdLevel, serial, expireDate));
                    myTableView.setItems(data);
                }
                if (!dataFound) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("No Data Found");
                    alert.setHeaderText(null);
                    alert.setContentText("No data matching the search criteria found.");
                    alert.showAndWait();
                }
            }
            } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    private String getQuery(String selectedValue) {
        Map<String, String> colMapping = new HashMap<>();
        colMapping.put("Component", "ComponentType.TypeName");
        colMapping.put("PanelBarcode", "PCBBarcode.Barcode");
        colMapping.put("ShopOrder", "[Order].Name");
        colMapping.put("PanelName", "Panel.Name");
        colMapping.put("BoardBarcode", "TracePanel.Barcode");
        colMapping.put("RefDesignator", "RefDesignator.Name");
        colMapping.put("ComponentBarcode", "PackagingUnit.ComponentBarcode");
        colMapping.put("Batch", "PackagingUnit.Batch");
        colMapping.put("OriginalQuantity", "PackagingUnit.OriginalQuantity");
        colMapping.put("PackagingUID", "PackagingUnit.PackagingUniqueID");
        colMapping.put("ManufactureData", "PackagingUnit.ManufactureDate");
        colMapping.put("MSDLevel", "PackagingUnit.MsdLevel");
        colMapping.put("Serial", "PackagingUnit.Serial");
        colMapping.put("ExipryDate", "PackagingUnit.ExpiryDate");

        if (selectedValue.equals("ShopOrder")) {
            System.out.println("SO selected");
            return "SELECT DISTINCT\n" +
                    "  PCBBarcode.Barcode AS Panel_Barcode,\n" +
                    "  TracePanel.Barcode AS Board_Barcode\n" +
                    "FROM\n" +
                    "  PCBBarcode\n" +
                    "  JOIN TraceData ON TraceData.PCBBarcodeId = PCBBarcode.Id\n" +
                    "  JOIN TracePanel ON TracePanel.TraceDataId = TraceData.Id\n" +
                    "  JOIN Panel ON Panel.Id = TracePanel.PanelId\n" +
                    "  JOIN TraceJob ON TraceJob.TraceDataId = TraceData.Id\n" +
                    "  JOIN Job ON Job.Id = TraceJob.JobId\n" +
                    "  JOIN [Order] ON [Order].Id = Job.OrderId\n" +
                    "WHERE\n" +
                    "[Order].Name" + " = ?\n" +
                    "AND TracePanel.Barcode <> ''\n"+
                    "Order BY\n" +
                    "Board_Barcode ASC;";
        } else {
            return "SELECT DISTINCT\n" +
                    "  ComponentType.TypeName AS Component,\n" +
                    "  PCBBarcode.Barcode AS Panel_Barcode,\n" +
                    "  Panel.Name AS Panel_Name,\n" +
                    "  TracePanel.Barcode AS Board_Barcode,\n" +
                    "  RefDesignator.Name AS RefDesignator,\n" +
                    "  PackagingUnit.ComponentBarcode AS Component_Barcode,\n" +
                    "  PackagingUnit.Batch As Batch,\n" +
                    "  PackagingUnit.OriginalQuantity AS Original_Quantity,\n" +
                    "  PackagingUnit.PackagingUniqueId,\n" +
                    "  PackagingUnit.ManufactureDate as Manufacture_Date,\n" +
                    "  PackagingUnit.MsdLevel,\n" +
                    "  PackagingUnit.Serial,\n" +
                    "  --Manufacturer.Name AS Manufacturer\n" +
                    "  --Supplier.Name AS Supplier,\n" +
                    "  PackagingUnit.ExpiryDate as Expiry_Date\n" +
                    "\n" +
                    "FROM\n" +
                    "  PCBBarcode\n" +
                    "  JOIN TraceData ON TraceData.PCBBarcodeId = PCBBarcode.Id\n" +
                    "  JOIN TracePanel ON TracePanel.TraceDataId = TraceData.Id\n" +
                    "  JOIN Placement ON Placement.PanelId = TracePanel.PanelId\n" +
                    "  JOIN Charge ON Charge.Id = Placement.ChargeId\n" +
                    "  JOIN PackagingUnit ON PackagingUnit.Id = Charge.PackagingUnitId\n" +
                    "  JOIN ComponentType ON ComponentType.Id = PackagingUnit.ComponentTypeId\n" +
                    "  JOIN Panel ON Panel.Id = TracePanel.PanelId\n" +
                    "  JOIN RefDesignator ON RefDesignator.Id = Placement.RefDesignatorId\n" +
                    "  --JOIN Manufacturer ON Manufacturer.Id = PackagingUnit.ManufacturerId\n" +
                    "  --JOIN Supplier ON PackagingUnit.SupplierId = Supplier.Id\n" +
                    "  JOIN TraceJob ON TraceJob.TraceDataId = TraceData.Id\n" +
                    "  JOIN Job ON Job.Id = TraceJob.JobId\n" +
                    "  JOIN [Order] ON [Order].Id = Job.OrderId" +
                    "\n" +
                    " \n" +
                    "  where " + colMapping.get(selectedValue) + " = ?\n" +
                    "  ORDER BY\n" +
                    "  component ASC,\n" +
                    "  Panel_Name ASC,\n" +
                    "  Board_Barcode ASC;";
        }
    }


//        FilteredList<Data> filteredData = new FilteredList<>(data, p -> true);
//        if (!searchTextValue.isEmpty()) {
//            switch (selectedValue) {
//                case "Component":
//                    filteredData.setPredicate(p -> {
//                        String component = p.getComponent();
//                        if (component == null) {
//                            return false;
//                        }
//                        return component.contains(searchTextValue);
//                    });
//                    break;
//                case "PanelName":
//                    filteredData.setPredicate(p -> {
//                        String panelName = p.getPanel_Name();
//                        if (panelName == null) {
//                            return false;
//                        }
//                        return panelName.contains(searchTextValue);
//                    });
//                    break;
//                case "BoardBarcode":
//                    filteredData.setPredicate(p -> {
//                        String boardBarcode = p.getBoard_Barcode();
//                        if (boardBarcode == null) {
//                            return false;
//                        }
//                        return boardBarcode.contains(searchTextValue);
//                    });
//                    break;
//                case "Station":
//                    filteredData.setPredicate(p -> {
//                        String station = p.getStation();
//                        if (station == null) {
//                            return false;
//                        }
//                        return station.contains(searchTextValue);
//                    });
//                    break;
//                case "MatrixIndexX":
//                    filteredData.setPredicate(p -> {
//                        String matrixIndexX = p.getMatrix_Index_X();
//                        if (matrixIndexX == null) {
//                            return false;
//                        }
//                        return matrixIndexX.contains(searchTextValue);
//                    });
//                    break;
//                case "MatrixIndexY":
//                    filteredData.setPredicate(p -> {
//                        String matrixIndexY = p.getMatrix_Index_Y();
//                        if (matrixIndexY == null) {
//                            return false;
//                        }
//                        return matrixIndexY.contains(searchTextValue);
//                    });
//                    break;
//                case "RefDesignator":
//                    filteredData.setPredicate(p -> {
//                        String refDesignator = p.getRef_Designator();
//                        if (refDesignator == null) {
//                            return false;
//                        }
//                        return refDesignator.contains(searchTextValue);
//                    });
//                    break;
//                case "ComponentBarcode":
//                    filteredData.setPredicate(p -> {
//                        String componentBarcode = p.getComponent_Barcode();
//                        if (componentBarcode == null) {
//                            return false;
//                        }
//                        return componentBarcode.contains(searchTextValue);
//                    });
//                    break;
//                case "Batch":
//                    filteredData.setPredicate(p -> {
//                        String batch = p.getBatch();
//                        if (batch == null) {
//                            return false;
//                        }
//                        return batch.contains(searchTextValue);
//                    });
//                    break;
//                case "OriginalQuanity":
//                    filteredData.setPredicate(p -> {
//                        String originalQuanity = p.getOriginal_Quanity();
//                        if (originalQuanity == null) {
//                            return false;
//                        }
//                        return originalQuanity.contains(searchTextValue);
//                    });
//                    break;
//                case "PackagingUID":
//                    filteredData.setPredicate(p -> {
//                        String packagingUid = p.getPackaging_UID();
//                        if (packagingUid == null) {
//                            return false;
//                        }
//                        return packagingUid.contains(searchTextValue);
//                    });
//                    break;
//                case "ManufactureDate":
//                    filteredData.setPredicate(p -> {
//                        String manufactureDate = p.getManufacture_Date();
//                        if (manufactureDate == null) {
//                            return false;
//                        }
//                        return manufactureDate.contains(searchTextValue);
//                    });
//                    break;
//                case "Manufacturer":
//                    filteredData.setPredicate(p -> {
//                        String manufacturer = p.getManufacturer();
//                        if (manufacturer == null) {
//                            return false;
//                        }
//                        return manufacturer.contains(searchTextValue);
//                    });
//                    break;
//                case "MSDLevel":
//                    filteredData.setPredicate(p -> {
//                        String msdLevel = p.getMSD_Level();
//                        if (msdLevel == null) {
//                            return false;
//                        }
//                        return msdLevel.contains(searchTextValue);
//                    });
//                    break;
//                case "Serial":
//                    filteredData.setPredicate(p -> {
//                        String serial = p.getSerial();
//                        if (serial == null) {
//                            return false;
//                        }
//                        return serial.contains(searchTextValue);
//                    });
//                    break;
//                case "Supplier":
//                    filteredData.setPredicate(p -> {
//                        String supplier = p.getSupplier();
//                        if (supplier == null) {
//                            return false;
//                        }
//                        return supplier.contains(searchTextValue);
//                    });
//                    break;
//                case "ExpireDate":
//                    filteredData.setPredicate(p -> {
//                        String expireDate = p.getExpire_Date();
//                        if (expireDate == null) {
//                            return false;
//                        }
//                        return expireDate.contains(searchTextValue);
//                    });
//                    break;
//                default:
//                    filteredData.setPredicate(p -> true);
//                    break;
//            }
//            myTableView.setItems(filteredData);
//        } else {
//            myTableView.setItems(data);
//        }

    @FXML
    void btnResetClicked(ActionEvent event) {
        searchText.clear();
        data.clear();
        initialize();
        myChoiceBox.setValue("Component");
    }
    @FXML
    void btnExportClicked(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save as Excel");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Excel Files", "*.xlsx")
        );
        File file = fileChooser.showSaveDialog(myTableView.getScene().getWindow());

        if(file != null){
            XSSFWorkbook workbook = new XSSFWorkbook();
            XSSFSheet sheet = workbook.createSheet("Data");
            XSSFRow header = sheet.createRow(0);
            ObservableList<TableColumn<Data, ?>> columns = myTableView.getColumns();
            for(int i = 0; i < columns.size(); i++){
                header.createCell(i).setCellValue(columns.get(i).getText());
            }
            ObservableList<Data> rows = myTableView.getItems();
            for(int i = 0; i < rows.size(); i++){
                XSSFRow sheetRow = sheet.createRow(i+1);
                Data rowData = rows.get(i);
                for(int j = 0; j < columns.size(); j++){
                    TableColumn<Data, ?> column = columns.get(j);
                    ObservableValue<?> cellValue = column.getCellObservableValue(rowData);
                    if(cellValue != null && cellValue.getValue() != null){
                        String cellString = cellValue.getValue().toString();
                        sheetRow.createCell(j).setCellValue(cellString);
                    }
                }
            }
            try {
                FileOutputStream fileOut = new FileOutputStream(file);
                workbook.write(fileOut);
                fileOut.close();
                workbook.close();
            }catch (IOException e){
                e.printStackTrace();
            }

        }
    }

}
