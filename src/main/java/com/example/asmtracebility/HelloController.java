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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
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
    private TableColumn<Data, String> colPanelId;
    @FXML
    private TableColumn<Data, String> colPcbId;
    @FXML
    private TableColumn<Data, String> colPanelName;
    @FXML
    private TableColumn<Data, String> colShopOrder;
    @FXML
    private TableColumn<Data, String> colComponentPN;
    @FXML
    private TableColumn<Data, String> colRefDesignator;
    @FXML
    private TableColumn<Data, String> colReelId;
    @FXML
    private TableColumn<Data, String> colTableId;
    @FXML
    private TableColumn<Data, String> colTrack;
    @FXML
    private TableColumn<Data, String> colDiv;
    @FXML
    private TableColumn<Data, String> colTower;
    @FXML
    private TableColumn<Data, String> colLevel;
    @FXML
    private TableColumn<Data, String> colOriginalQuantity;
    @FXML
    private TableColumn<Data, String> colLotCode;
    @FXML
    private TableColumn<Data, String> colDateCode;
    @FXML
    private TableColumn<Data, String> colSupplier;
    @FXML
    private TableColumn<Data, String> colStation;
    @FXML
    private TableColumn<Data, String> colMsdLevel;
    @FXML
    private TableColumn<Data, String> colProgram;
    @FXML
    private TableColumn<Data, String> colBeginDate;
    @FXML
    private TableColumn<Data, String> colEndDate;

    @FXML
    private TextField searchText;

    ObservableList<Data> data = FXCollections.observableArrayList();
    List<String> searchOptions = Arrays.asList(
            "panelId",
            "pcbId",
            "panelName",
            "shopOrder",
            "componentPN",
            "refDesignator",
            "reelId",
            "tableId",
            "track",
            "div",
            "tower",
            "level",
            "originalQuantity",
            "lotCode",
            "dateCode",
            "supplier",
            "station",
            "msdLevel",
            "program",
            "beginDate",
            "endDate"
    );

    @FXML
    private ChoiceBox<String> myChoiceBox;

    @FXML
    private void initialize(){
        borderPane.setCenter(myAnchorPane);
        myChoiceBox.setValue("pcbId");
        myChoiceBox.setItems(FXCollections.observableArrayList(searchOptions));
        colPanelId.setCellValueFactory(new PropertyValueFactory<>("panelId"));
        colPcbId.setCellValueFactory(new PropertyValueFactory<>("pcbId"));
        colPanelName.setCellValueFactory(new PropertyValueFactory<>("panelName"));
        colShopOrder.setCellValueFactory(new PropertyValueFactory<>("shopOrder"));
        colComponentPN.setCellValueFactory(new PropertyValueFactory<>("componentPN"));
        colRefDesignator.setCellValueFactory(new PropertyValueFactory<>("refDesignator"));
        colReelId.setCellValueFactory(new PropertyValueFactory<>("reelId"));
        colTableId.setCellValueFactory(new PropertyValueFactory<>("tableId"));
        colTrack.setCellValueFactory(new PropertyValueFactory<>("track"));
        colDiv.setCellValueFactory(new PropertyValueFactory<>("div"));
        colTower.setCellValueFactory(new PropertyValueFactory<>("tower"));
        colLevel.setCellValueFactory(new PropertyValueFactory<>("level"));
        colOriginalQuantity.setCellValueFactory(new PropertyValueFactory<>("originalQuantity"));
        colLotCode.setCellValueFactory(new PropertyValueFactory<>("lotCode"));
        colDateCode.setCellValueFactory(new PropertyValueFactory<>("dateCode"));
        colSupplier.setCellValueFactory(new PropertyValueFactory<>("supplier"));
        colStation.setCellValueFactory(new PropertyValueFactory<>("station"));
        colMsdLevel.setCellValueFactory(new PropertyValueFactory<>("msdLevel"));
        colProgram.setCellValueFactory(new PropertyValueFactory<>("program"));
        colBeginDate.setCellValueFactory(new PropertyValueFactory<>("beginDate"));
        colEndDate.setCellValueFactory(new PropertyValueFactory<>("endDate"));

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

            if(selectedValue.equals("pcbId")){
                myTableView.getColumns().setAll(colPanelId, colPcbId, colPanelName, colShopOrder, colComponentPN, colRefDesignator,
                        colReelId, colTableId, colTrack, colDiv, colTower, colLevel, colOriginalQuantity, colLotCode,
                        colDateCode, colSupplier, colStation, colMsdLevel, colProgram, colBeginDate, colEndDate);
                statement.setString(1, searchTextValue);

                ResultSet resultSet = statement.executeQuery();

                if(resultSet.next()) {
                    String dbpanelId = resultSet.getString("Panel_ID");

                    //use the retrieved panlId in subsequent query
                    String subsequentQuery = "SELECT\n" +
                            "  PCBBarcode.Barcode AS Panel_ID,\n" +
                            "  TracePanel.Barcode AS PCB_ID,\n" +
                            "  Panel.Name AS Panel_Name,\n" +
                            "  [Order].Name AS Shop_Order,\n" +
                            "  ComponentType.TypeName AS Component_PN,\n" +
                            "  RefDesignator.Name AS RefDesignator,\n" +
                            "  PackagingUnit.Serial AS Reel_ID,\n" +
                            "  TableBarcode.Barcode AS Table_ID,\n" +
                            "  [Location].Track AS Track,\n" +
                            "  [Location].Div AS Div,\n" +
                            "  [Location].Tower AS Tower,\n" +
                            "  [Location].Level AS Level,\n" +
                            "  PackagingUnit.OriginalQuantity AS Original_Quantity,\n" +
                            "  PackagingUnit.Batch AS Lot_Code,\n" +
                            "  RIGHT(CAST(YEAR(PackagingUnit.ManufactureDate) AS VARCHAR(4)), 2) + RIGHT('0' + CAST(DATEPART(WEEK, PackagingUnit.ManufactureDate) AS VARCHAR(2)), 2) AS Date_Code,\n" +
                            "  Manufacturer.Name AS Supplier,\n" +
                            "  Station.Name AS Station,\n" +
                            "  PackagingUnit.MsdLevel AS Msd_Level,\n" +
                            "  Recipe.Name AS Program,\n" +
                            "  TraceData.BeginDate AS Begin_Date,\n" +
                            "  TraceData.EndDate AS End_Date\n" +
                            "FROM\n" +
                            "  PCBBarcode\n" +
                            "  LEFT JOIN TraceData ON TraceData.PCBBarcodeId = PCBBarcode.Id\n" +
                            "  LEFT JOIN TracePanel ON TracePanel.TraceDataId = TraceData.Id\n" +
                            "  LEFT JOIN Placement ON Placement.PanelId = TracePanel.PanelId\n" +
                            "  LEFT JOIN Charge ON Charge.Id = Placement.ChargeId\n" +
                            "  LEFT JOIN PackagingUnit ON PackagingUnit.Id = Charge.PackagingUnitId\n" +
                            "  LEFT JOIN ComponentType ON ComponentType.Id = PackagingUnit.ComponentTypeId\n" +
                            "  LEFT JOIN Panel ON Panel.Id = TracePanel.PanelId\n" +
                            "  LEFT JOIN RefDesignator ON RefDesignator.Id = Placement.RefDesignatorId\n" +
                            "  LEFT JOIN TraceJob ON TraceJob.TraceDataId = TraceData.Id\n" +
                            "  LEFT JOIN Job ON Job.Id = TraceJob.JobId\n" +
                            "  LEFT JOIN [Order] ON [Order].Id = Job.OrderId\n" +
                            "  LEFT JOIN PlacementGroup ON PlacementGroup.Id = Placement.PlacementGroupId\n" +
                            "  LEFT JOIN Station ON Station.Id = TraceData.StationId\n" +
                            "  LEFT JOIN Manufacturer ON PackagingUnit.ManufacturerId = Manufacturer.Id\n" +
                            "  LEFT JOIN Recipe ON Job.RecipeId = Recipe.id\n" +
                            "  LEFT JOIN [Location] ON Charge.LocationId = [Location].Id\n" +
                            "  LEFT JOIN TableBarcode ON [Location].TableBarcodeID = TableBarcode.id\n" +
                            "WHERE\n" +
                            "  PlacementGroup.Id IN (\n" +
                            "    SELECT PlacementGroupId FROM TracePlacement WHERE TraceDataId = TraceData.Id\n" +
                            "  )\n" +
                            "AND " + "PCBBarcode.Barcode = ?\n" +
                            "ORDER BY\n" +
                            "  Component_PN ASC,\n" +
                            "  Panel_Name ASC,\n" +
                            "  PCB_ID ASC;";

                    PreparedStatement subsequentStatement = connection.prepareStatement(subsequentQuery);
                    subsequentStatement.setString(1, dbpanelId);

                    ResultSet subsequentResultSet = subsequentStatement.executeQuery();

                    while (subsequentResultSet.next()) {
                        String panelId = subsequentResultSet.getString("Panel_ID");
                        String pcbId = subsequentResultSet.getString("PCB_ID");
                        String panelName = subsequentResultSet.getString("Panel_Name");
                        String shopOrder = subsequentResultSet.getString("Shop_Order");
                        String componentPN = subsequentResultSet.getString("component_PN");
                        String refDesignator = subsequentResultSet.getString("refDesignator");
                        String reelId = subsequentResultSet.getString("Reel_ID");
                        String tableId = subsequentResultSet.getString("Table_ID");
                        String track = subsequentResultSet.getString("Track");
                        String div = subsequentResultSet.getString("Div");
                        String tower = subsequentResultSet.getString("Tower");
                        String level = subsequentResultSet.getString("Level");
                        String originalQuantity = subsequentResultSet.getString("Original_Quantity");
                        String lotCode = subsequentResultSet.getString("Lot_Code");
                        String dateCode = subsequentResultSet.getString("Date_Code");
                        String supplier = subsequentResultSet.getString("Supplier");
                        String station = subsequentResultSet.getString("Station");
                        String msdLevel = subsequentResultSet.getString("Msd_Level");
                        String program = subsequentResultSet.getString("Program");
                        String beginDate = subsequentResultSet.getString("Begin_Date");
                        String endDate = subsequentResultSet.getString("End_Date");

                        //System.out.println("Imhere");
                        dataFound = true;
                        data.add(new Data(panelId, pcbId, panelName, shopOrder, componentPN, refDesignator, reelId, tableId, track, div, tower, level, originalQuantity, lotCode, dateCode, supplier, station, msdLevel, program, beginDate, endDate));

                    }
                }
                    processData();
                    filterPCB(searchTextValue);
                    if (!dataFound) {
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("No Data Found");
                        alert.setHeaderText(null);
                        alert.setContentText("No data matching the search criteria found.");
                        alert.showAndWait();
                    }
            } else if (selectedValue.equals("ShopOrder")) {
                statement.setString(1,searchTextValue);
                // Remove the unnecessary columns
                myTableView.getColumns().removeAll(colPanelId, colPcbId, colPanelName, colShopOrder, colComponentPN, colRefDesignator,
                        colReelId, colTableId, colTrack, colDiv, colTower, colLevel, colOriginalQuantity, colLotCode,
                        colDateCode, colSupplier, colStation, colMsdLevel, colProgram, colBeginDate, colEndDate);

                // Add the necessary columns
                myTableView.getColumns().addAll(colPanelId, colPcbId);

                ResultSet resultSet = statement.executeQuery();

                while (resultSet.next()) {
                    String panelId = resultSet.getString("Panel_ID");
                    String pcbId = resultSet.getString("PCB_ID");

                   data.add(new Data(panelId, pcbId));

                    dataFound = true;
                }
                if (!dataFound) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("No Data Found");
                    alert.setHeaderText(null);
                    alert.setContentText("No data matching the search criteria found.");
                    alert.showAndWait();
                }
                myTableView.setItems(data);
            }else {
                myTableView.getColumns().setAll(colPanelId, colPcbId, colPanelName, colShopOrder, colComponentPN, colRefDesignator,
                        colReelId, colTableId, colTrack, colDiv, colTower, colLevel, colOriginalQuantity, colLotCode,
                        colDateCode, colSupplier, colStation, colMsdLevel, colProgram, colBeginDate, colEndDate);

                statement.setString(1,searchTextValue);

                ResultSet resultSet = statement.executeQuery();
                while (resultSet.next()) {
                    String panelId = resultSet.getString("Panel_ID");
                    String pcbId = resultSet.getString("PCB_ID");
                    String panelName = resultSet.getString("Panel_Name");
                    String shopOrder = resultSet.getString("Shop_Order");
                    String componentPN = resultSet.getString("component_PN");
                    String refDesignator = resultSet.getString("refDesignator");
                    String reelId = resultSet.getString("Reel_ID");
                    String tableId = resultSet.getString("Table_ID");
                    String track = resultSet.getString("Track");
                    String div = resultSet.getString("Div");
                    String tower = resultSet.getString("Tower");
                    String level = resultSet.getString("Level");
                    String originalQuantity = resultSet.getString("Original_Quantity");
                    String lotCode = resultSet.getString("Lot_Code");
                    String dateCode = resultSet.getString("Date_Code");
                    String supplier = resultSet.getString("Supplier");
                    String station = resultSet.getString("Station");
                    String msdLevel = resultSet.getString("Msd_Level");
                    String program = resultSet.getString("Program");
                    String beginDate = resultSet.getString("Begin_Date");
                    String endDate = resultSet.getString("End_Date");



                    dataFound = true;
                    data.add(new Data(panelId, pcbId, panelName, shopOrder, componentPN, refDesignator, reelId, tableId, track, div, tower, level, originalQuantity, lotCode, dateCode, supplier, station, msdLevel, program, beginDate, endDate));

                }
                processData();
                myTableView.setItems(data);
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

    public void filterPCB(String searchTextValue){
        List<Data> filteredData = new ArrayList<>();
        for (Data data: data){
            if (data.getPcbId().equals(searchTextValue)){
                filteredData.add(data);
            }
        }
        myTableView.setItems(FXCollections.observableArrayList(filteredData));
    }

    public void processData(){
        for (Data topData : data) {
            // Check if the boardbarcode is empty
            if (topData.getPcbId().isEmpty()) {
                String panelId = topData.getPanelId();
                String topPanelName = topData.getPanelName();
                String topPanelNumber = extractPanelNumber(topPanelName);
                // Find the corresponding "bot" or "top" entry
                boolean matchFound = false;
                for (Data otherData : data) {
                    if (otherData.getPanelId().equals(panelId) && hasSamePanelNumber(otherData.getPanelName(), topPanelNumber)) {
                        if(!otherData.getPcbId().isEmpty()){
                            topData.setPcbId(otherData.getPcbId());
                            continue;
                        }
                        matchFound = true;
                        break; // Exit the inner loop once a match is found
                    }
                }

                }
            }
        }

    private String extractPanelNumber(String panelName) {
        StringBuilder panelNumbers = new StringBuilder();
        String[] parts = panelName.split("_");
        if (parts.length >= 2) {
            for (int i = 1; i < parts.length; i++) {
                String panelNumber = parts[i];
                if (panelNumber.matches("\\d+")) {
                    panelNumbers.append(panelNumber);
                }
            }
        }
        return panelNumbers.toString();
    }
    private boolean hasSamePanelNumber(String panelName, String panelNumber) {
        String[] parts = panelName.split("_");
        if (parts.length >= 2) {
            String otherPanelNumber = extractPanelNumber(panelName);
            return otherPanelNumber.equals(panelNumber);
        }
        return false;
    }
    private String getQuery(String selectedValue) {
        Map<String, String> colMapping = new HashMap<>();
        colMapping.put("panelId", "PCBBarcode.Barcode");
        colMapping.put("pcbId", "TracePanel.Barcode");
        colMapping.put("shopOrder", "[Order].Name");
        colMapping.put("componentPN", "Component.TypeName");
        colMapping.put("refDesignator", "RefDesignator.Name");
        colMapping.put("reelId", "PackagingUnit.Serial");
        colMapping.put("tableId", "TableBarcode.Barcode");
        colMapping.put("track", "[Location].Track");
        colMapping.put("div", "[Location].Div");
        colMapping.put("tower", "[Location].Tower");
        colMapping.put("level", "[Location].Level");
        colMapping.put("lotCode", "PackagingUnit.Batch");
        colMapping.put("dateCode", "PackagingUnit.ManufactureDate");
        colMapping.put("supplier", "Manufacturer.Name");
        colMapping.put("station", "Station.Name");
        colMapping.put("msdLevel", "PackagingUnit.MsdLevel");
        colMapping.put("program", "Recipe.Name");

        if(selectedValue.equals("dateCode")) {
            return  "SELECT\n" +
                    "  PCBBarcode.Barcode AS Panel_ID,\n" +
                    "  TracePanel.Barcode AS PCB_ID,\n" +
                    "  Panel.Name AS Panel_Name,\n" +
                    "  [Order].Name AS Shop_Order,\n" +
                    "  ComponentType.TypeName AS Component_PN,\n" +
                    "  RefDesignator.Name AS RefDesignator,\n" +
                    "  PackagingUnit.Serial AS Reel_ID,\n" +
                    "  TableBarcode.Barcode AS Table_ID,\n" +
                    "  [Location].Track AS Track,\n" +
                    "  [Location].Div AS Div,\n" +
                    "  [Location].Tower AS Tower,\n" +
                    "  [Location].Level AS Level,\n" +
                    "  PackagingUnit.OriginalQuantity AS Original_Quantity,\n" +
                    "  PackagingUnit.Batch AS Lot_Code,\n" +
                    "  RIGHT(CAST(YEAR(PackagingUnit.ManufactureDate) AS VARCHAR(4)), 2) + RIGHT('0' + CAST(DATEPART(WEEK, PackagingUnit.ManufactureDate) AS VARCHAR(2)), 2) AS Date_Code,\n" +
                    "  Manufacturer.Name AS Supplier,\n" +
                    "  Station.Name AS Station,\n" +
                    "  PackagingUnit.MsdLevel AS Msd_Level,\n" +
                    "  Recipe.Name AS Program,\n" +
                    "  TraceData.BeginDate AS Begin_Date,\n" +
                    "  TraceData.EndDate AS End_Date\n" +
                    "FROM\n" +
                    "  PCBBarcode\n" +
                    "  LEFT JOIN TraceData ON TraceData.PCBBarcodeId = PCBBarcode.Id\n" +
                    "  LEFT JOIN TracePanel ON TracePanel.TraceDataId = TraceData.Id\n" +
                    "  LEFT JOIN Placement ON Placement.PanelId = TracePanel.PanelId\n" +
                    "  LEFT JOIN Charge ON Charge.Id = Placement.ChargeId\n" +
                    "  LEFT JOIN PackagingUnit ON PackagingUnit.Id = Charge.PackagingUnitId\n" +
                    "  LEFT JOIN ComponentType ON ComponentType.Id = PackagingUnit.ComponentTypeId\n" +
                    "  LEFT JOIN Panel ON Panel.Id = TracePanel.PanelId\n" +
                    "  LEFT JOIN RefDesignator ON RefDesignator.Id = Placement.RefDesignatorId\n" +
                    "  LEFT JOIN TraceJob ON TraceJob.TraceDataId = TraceData.Id\n" +
                    "  LEFT JOIN Job ON Job.Id = TraceJob.JobId\n" +
                    "  LEFT JOIN [Order] ON [Order].Id = Job.OrderId\n" +
                    "  LEFT JOIN PlacementGroup ON PlacementGroup.Id = Placement.PlacementGroupId\n" +
                    "  LEFT JOIN Station ON Station.Id = TraceData.StationId\n" +
                    "  LEFT JOIN Manufacturer ON PackagingUnit.ManufacturerId = Manufacturer.Id\n" +
                    "  LEFT JOIN Recipe ON Job.RecipeId = Recipe.id\n" +
                    "  LEFT JOIN [Location] ON Charge.LocationId = [Location].Id\n" +
                    "  LEFT JOIN TableBarcode ON [Location].TableBarcodeID = TableBarcode.id\n" +
                    "WHERE\n" +
                    "  PlacementGroup.Id IN (\n" +
                    "    SELECT PlacementGroupId FROM TracePlacement WHERE TraceDataId = TraceData.Id\n" +
                    "  )\n" +
                    "AND RIGHT(CAST(YEAR(PackagingUnit.ManufactureDate) AS VARCHAR(4)), 2) + RIGHT('0' + CAST(DATEPART(WEEK, PackagingUnit.ManufactureDate) AS VARCHAR(2)), 2) = ?\n" +
                    "ORDER BY\n" +
                    "  Component_PN ASC,\n" +
                    "  Panel_Name ASC,\n" +
                    "  PCB_ID ASC;\n";
        }else if (selectedValue.equals("shopOrder")) {
            return "SELECT DISTINCT\n" +
                    "  PCBBarcode.Barcode AS Panel_Barcode,\n" +
                    "  TracePanel.Barcode AS Board_Barcode\n" +
                    "FROM\n" +
                    "  PCBBarcode\n" +
                    "  LEFT JOIN TraceData ON TraceData.PCBBarcodeId = PCBBarcode.Id\n" +
                    "  LEFT JOIN TracePanel ON TracePanel.TraceDataId = TraceData.Id\n" +
                    "  LEFT JOIN Placement ON Placement.PanelId = TracePanel.PanelId\n" +
                    "  LEFT JOIN Charge ON Charge.Id = Placement.ChargeId\n" +
                    "  LEFT JOIN PackagingUnit ON PackagingUnit.Id = Charge.PackagingUnitId\n" +
                    "  LEFT JOIN ComponentType ON ComponentType.Id = PackagingUnit.ComponentTypeId\n" +
                    "  LEFT JOIN Panel ON Panel.Id = TracePanel.PanelId\n" +
                    "  LEFT JOIN RefDesignator ON RefDesignator.Id = Placement.RefDesignatorId\n" +
                    "  LEFT JOIN TraceJob ON TraceJob.TraceDataId = TraceData.Id\n" +
                    "  LEFT JOIN Job ON Job.Id = TraceJob.JobId\n" +
                    "  LEFT JOIN [Order] ON [Order].Id = Job.OrderId\n" +
                    "  LEFT JOIN PlacementGroup ON PlacementGroup.Id = Placement.PlacementGroupId\n" +
                    "WHERE\n" +
                    "[Order].Name" + " = ?\n" +
                    "AND TracePanel.Barcode <> ''\n"+
                    "Order BY\n" +
                    "Board_Barcode ASC;";
        } else if (selectedValue.equals("pcbId")){
            return "SELECT\n" +
                    "  PCBBarcode.Barcode AS Panel_ID,\n" +
                    "  TracePanel.Barcode AS PCB_ID\n" +
                    "FROM\n" +
                    "  PCBBarcode\n" +
                    "  LEFT JOIN TraceData ON TraceData.PCBBarcodeId = PCBBarcode.Id\n" +
                    "  LEFT JOIN TracePanel ON TracePanel.TraceDataId = TraceData.Id\n" +
                    "  LEFT JOIN Panel ON Panel.Id = TracePanel.PanelId\n" +
                    "WHERE\n" +
                    "TracePanel.Barcode = ?\n";
        } else {
            return "SELECT\n" +
                    "  PCBBarcode.Barcode AS Panel_ID,\n" +
                    "  TracePanel.Barcode AS PCB_ID,\n" +
                    "   Panel.Name AS Panel_Name,\n" +
                    "   [Order].Name AS Shop_Order,\n" +
                    "  ComponentType.TypeName AS Component_PN,\n" +
                    "  RefDesignator.Name AS RefDesignator,\n" +
                    "  PackagingUnit.Serial AS Reel_ID,\n" +
                    "  TableBarcode.Barcode AS Table_ID,\n" +
                    "  [Location].Track AS Track,\n" +
                    "  [Location].Div AS Div,\n" +
                    "  [Location].Tower AS Tower,\n" +
                    "  [Location].Level AS Level,\n" +
                    "  PackagingUnit.OriginalQuantity AS Original_Quantity,\n" +
                    "  PackagingUnit.Batch AS Lot_Code,\n" +
                    "  RIGHT(CAST(YEAR(PackagingUnit.ManufactureDate) AS VARCHAR(4)), 2) + RIGHT('0' + CAST(DATEPART(WEEK, PackagingUnit.ManufactureDate) AS VARCHAR(2)), 2) AS Date_Code,\n" +
                    "  Manufacturer.Name AS Supplier,\n" +
                    "  Station.Name AS Station,\n" +
                    "  PackagingUnit.MsdLevel AS Msd_Level,\n" +
                    "  Recipe.Name AS Program,\n" +
                    "  TraceData.BeginDate AS Begin_Date,\n" +
                    "  TraceData.EndDate AS End_Date\n" +
                    "FROM\n" +
                    "  PCBBarcode\n" +
                    "  LEFT JOIN TraceData ON TraceData.PCBBarcodeId = PCBBarcode.Id\n" +
                    "  LEFT JOIN TracePanel ON TracePanel.TraceDataId = TraceData.Id\n" +
                    "  LEFT JOIN Placement ON Placement.PanelId = TracePanel.PanelId\n" +
                    "  LEFT JOIN Charge ON Charge.Id = Placement.ChargeId\n" +
                    "  LEFT JOIN PackagingUnit ON PackagingUnit.Id = Charge.PackagingUnitId\n" +
                    "  LEFT JOIN ComponentType ON ComponentType.Id = PackagingUnit.ComponentTypeId\n" +
                    "  LEFT JOIN Panel ON Panel.Id = TracePanel.PanelId\n" +
                    "  LEFT JOIN RefDesignator ON RefDesignator.Id = Placement.RefDesignatorId\n" +
                    "  LEFT JOIN TraceJob ON TraceJob.TraceDataId = TraceData.Id\n" +
                    "  LEFT JOIN Job ON Job.Id = TraceJob.JobId\n" +
                    "  LEFT JOIN [Order] ON [Order].Id = Job.OrderId\n" +
                    "  LEFT JOIN PlacementGroup ON PlacementGroup.Id = Placement.PlacementGroupId\n" +
                    "  LEFT JOIN Station ON Station.Id = TraceData.StationId\n" +
                    "  LEFT JOIN Manufacturer ON PackagingUnit.ManufacturerId = Manufacturer.Id\n" +
                    "  LEFT JOIN Recipe ON Job.RecipeId = Recipe.id\n" +
                    "  LEFT JOIN [Location] ON Charge.LocationId = [Location].Id\n" +
                    "  LEFT JOIN TableBarcode ON [Location].TableBarcodeID = TableBarcode.id\n" +
                    "WHERE\n" +
                    "  PlacementGroup.Id IN (\n" +
                    "    SELECT PlacementGroupId FROM TracePlacement WHERE TraceDataId = TraceData.Id\n" +
                    "  )\n" + "AND " +
                    colMapping.get(selectedValue) + " = ?\n" +
                    "ORDER BY\n" +
                    "  Component_PN ASC,\n" +
                    "  Panel_Name ASC,\n" +
                    "  PCB_ID ASC;\n";
        }
    }

    @FXML
    void btnResetClicked(ActionEvent event) {
        searchText.clear();
        data.clear();
        initialize();
        myChoiceBox.setValue("pcbId");
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
