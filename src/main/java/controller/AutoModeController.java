package controller;

import javafx.scene.control.TextArea;
import smo.App;
import javafx.fxml.FXML;
import smo.Statistics;
import smo.DeviceResult;
import smo.SourceResult;
import javafx.scene.control.TableView;
import javafx.collections.FXCollections;
import javafx.scene.control.TableColumn;
import javafx.collections.ObservableList;
import javafx.scene.control.cell.PropertyValueFactory;

public class AutoModeController {
    private App app;

    private Double dispW;
    private Double dis;
    private Double dispH;

    @FXML
    private TableView<SourceResult> sources;

    @FXML
    private TableView<DeviceResult> devices;

    @FXML
    private TableColumn<?, ?> sNum;

    @FXML
    private TableColumn<?, ?> genNum;

    @FXML
    private TableColumn<?, ?> rejectP;

    @FXML
    private TableColumn<?, ?> timeIn;

    @FXML
    private TableColumn<?, ?> waitTime;

    @FXML
    private TableColumn<?, ?> handleTime;

    @FXML
    private TableColumn<?, ?> devNum;

    @FXML
    private TableColumn<?, ?> coef;

    @FXML
    private TextArea dispWait;

    @FXML
    private TextArea dispHandle;

    @FXML
    void initialize() {
        sNum.setCellValueFactory(new PropertyValueFactory<>("sourceNum"));
        genNum.setCellValueFactory(new PropertyValueFactory<>("requestNum"));
        rejectP.setCellValueFactory(new PropertyValueFactory<>("failureProbability"));
        timeIn.setCellValueFactory(new PropertyValueFactory<>("averageTimeInSystem"));
        waitTime.setCellValueFactory(new PropertyValueFactory<>("averageWaitTime"));
        handleTime.setCellValueFactory(new PropertyValueFactory<>("averageHandleTime"));

        devNum.setCellValueFactory(new PropertyValueFactory<>("deviceNum"));
        coef.setCellValueFactory(new PropertyValueFactory<>("utilization"));
    }

    public void printInfo(Statistics statistics) {
        ObservableList<SourceResult> sourceResults = FXCollections.observableArrayList();
        sourceResults.addAll(statistics.getSourceResultList());
        this.sources.setItems(sourceResults);

        this.dispW = statistics.dispWaitTime();
        this.dispH = statistics.dispServTime();
        dispWait.appendText(dispW.toString());
        dispHandle.appendText(dispH.toString());

        ObservableList<DeviceResult> deviceResults = FXCollections.observableArrayList();
        deviceResults.addAll(statistics.getDeviceResultList());
        this.devices.setItems(deviceResults);

        System.out.println("Avg Device Usage: " + statistics.getAvgDeviceUsage());
        System.out.println("Avg Reject Probability: " + statistics.getAvgReject());
        System.out.println("Avg Time in System: " + statistics.getAvgSystemTime());
        System.out.println("-------------------------------------------------------\n");
    }

    @FXML
    void openMenu() {
        sources.refresh();
        devices.refresh();
        app.openMenu();
    }

    public void setApp(App app) {
        this.app = app;
    }
}
