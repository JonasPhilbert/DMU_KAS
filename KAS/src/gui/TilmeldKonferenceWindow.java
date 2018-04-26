package gui;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import application.Hotel;
import application.HotelTillaeg;
import application.Konference;
import application.Person;
import application.Service;
import application.Udflugt;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class TilmeldKonferenceWindow extends Stage {

    // TODO: Konferenceafgift
    // TODO: Speaker? checkbox

    protected Konference konference;

    private DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    private ConfirmPopup confirm;

    public TilmeldKonferenceWindow(String title, Stage owner) {
        initOwner(owner);
        initStyle(StageStyle.DECORATED);
        initModality(Modality.APPLICATION_MODAL);
        setMinHeight(100);
        setMinWidth(200);
        setResizable(false);

        confirm = new ConfirmPopup("Accepter Tilmelding", owner,
                "Er du sikker på at du vil\ntilmelde dig konferencen?");

        setTitle(title);
        GridPane pane = new GridPane();
        initContent(pane);

        Scene scene = new Scene(pane);
        setScene(scene);
    }

    private TextField[] txfDeltager = new TextField[4];
    private TextField[] txfLedsager = new TextField[4];
    private String[] txtTxfPerson = { "John", "Doe", "Lupinvej 24", "12345678" };
    private Label[] lblPerson = new Label[4];
    private String[] txtLblPerson = { "Fornavn:", "Efternavn:", "Adresse:", "Telefon:" };
    private CheckBox cbLedsager;
    private ComboBox<Hotel> cboxHotels;
    private ComboBox<HotelTillaeg> cboxTillaeg;
    private ListView<HotelTillaeg> lvwTillaeg;
    private ComboBox<Udflugt> cboxUdflugter;
    private ListView<Udflugt> lvwUdflugter;
    private Label lblTotalPrice;

    private void initContent(GridPane pane) {
        pane.setGridLinesVisible(false);
        pane.setPadding(new Insets(20));
        pane.setHgap(10);
        pane.setVgap(10);

        // Column #1
        // TODO: Tilføj pris i alt.

        Label lblRubrikDeltager = new Label("Indtast informationer (deltager):");
        pane.add(lblRubrikDeltager, 0, 0);

        int o = 1;
        for (int i = 0; i < 4; i++) {
            lblPerson[i] = new Label(txtLblPerson[i]);
            pane.add(lblPerson[i], 0, o);
            txfDeltager[i] = new TextField(txtTxfPerson[i]);
            pane.add(txfDeltager[i], 0, o + 1);
            o += 2;
        }

        Label lblHotel = new Label("Hotel:");
        pane.add(lblHotel, 0, 10);

        cboxHotels = new ComboBox<>();
        cboxHotels.getItems().addAll(Service.getHotels());
        cboxHotels.setOnAction(event -> cboxHotelsAction());
        pane.add(cboxHotels, 0, 11);

        cbLedsager = new CheckBox("Medbringer ledsager?");
        cbLedsager.setOnAction(event -> cbLedsagerAction());
        pane.add(cbLedsager, 0, 12);

        // Column #2

        Label lblRubrikLedsager = new Label("Indtast informationer (ledsager):");
        pane.add(lblRubrikLedsager, 1, 0);

        o = 1;
        for (int i = 0; i < 4; i++) {
            lblPerson[i] = new Label(txtLblPerson[i]);
            pane.add(lblPerson[i], 1, o);
            txfLedsager[i] = new TextField(txtTxfPerson[i]);
            pane.add(txfLedsager[i], 1, o + 1);
            txfLedsager[i].setDisable(true);
            o += 2;
        }

        // Column #3

        Label lblRubrikHotel = new Label("Vælg hotel-tillæg:");
        pane.add(lblRubrikHotel, 2, 0);

        Label lblHotelTillaeg = new Label("Mulige tillæg:");
        pane.add(lblHotelTillaeg, 2, 1);

        cboxTillaeg = new ComboBox<>();
        cboxTillaeg.setDisable(true);
        cboxTillaeg.setOnAction(event -> cboxTillaegAction());
        pane.add(cboxTillaeg, 2, 2);

        Label lblTillaegList = new Label("Valgte Tillæg:");
        pane.add(lblTillaegList, 2, 3);

        lvwTillaeg = new ListView<>();
        lvwTillaeg.setDisable(true);
        lvwTillaeg.setMaxHeight(200);
        pane.add(lvwTillaeg, 2, 4, 1, 8);

        Button btnRemoveTilaeg = new Button("Slet");
        btnRemoveTilaeg.setOnAction(event -> btnRemoveTilaegAction());
        pane.add(btnRemoveTilaeg, 2, 12);

        // Column #4

        Label lblRubrikUdflugt = new Label("Vælg ledsager-udflugter:");
        pane.add(lblRubrikUdflugt, 3, 0);

        Label lblUdflugter = new Label("Mulige udflugter:");
        pane.add(lblUdflugter, 3, 1);

        cboxUdflugter = new ComboBox<>();
        cboxUdflugter.setDisable(true);
        cboxUdflugter.setOnAction(event -> cboxUdflugterAction());
        pane.add(cboxUdflugter, 3, 2);

        lvwUdflugter = new ListView<>();
        lvwUdflugter = new ListView<>();
        lvwUdflugter.setDisable(true);
        lvwUdflugter.setMaxHeight(200);
        pane.add(lvwUdflugter, 3, 4, 1, 8);

        Button btnRemoveUdflugt = new Button("Slet");
        btnRemoveUdflugt.setOnAction(event -> btnRemoveUdflugtAction());
        pane.add(btnRemoveUdflugt, 3, 12);

        // Buttons

        Button btnCancel = new Button("Luk");
        pane.add(btnCancel, 0, 13);
        btnCancel.setOnAction(event -> btnCancelAction());

        Button btnAccept = new Button("Tilmeld");
        pane.add(btnAccept, 1, 13);
        btnAccept.setOnAction(event -> btnAcceptAction());

        // Total Price Label
        lblTotalPrice = new Label("TOTAL: 0.0");
        pane.add(lblTotalPrice, 3, 13);

    }

    private void btnRemoveTilaegAction() {
        lvwTillaeg.getItems().remove(lvwTillaeg.getSelectionModel().getSelectedItem());

        updateTotalPrice();
    }

    private void btnRemoveUdflugtAction() {
        lvwUdflugter.getItems().remove(lvwUdflugter.getSelectionModel().getSelectedItem());

        updateTotalPrice();
    }

    private void cboxHotelsAction() {
        cboxTillaeg.getItems().addAll(cboxHotels.getSelectionModel().getSelectedItem().getHotelTillaeg());
        cboxTillaeg.setDisable(false);

        updateTotalPrice();
    }

    private void cbLedsagerAction() {
        cboxUdflugter.getItems().addAll(konference.getUdflugter());

        if (cbLedsager.isSelected()) {
            for (TextField tf : txfLedsager) {
                tf.setDisable(false);
                lvwUdflugter.setDisable(false);
                cboxUdflugter.setDisable(false);
            }
        } else {
            for (TextField tf : txfLedsager) {
                tf.setDisable(true);
                lvwUdflugter.setDisable(true);
                cboxUdflugter.setDisable(true);
            }
        }

        updateTotalPrice();
    }

    private void btnAcceptAction() {
        updateTotalPrice();

        confirm.showAndWait();
        Person deltager = null;
        if (confirm.confirmed) {
            Service.createPerson(txfDeltager[0].getText(), txfDeltager[1].getText(), txfDeltager[2].getText(),
                    txfDeltager[3].getText());
            Person ledsager = null;
            if (cbLedsager.isSelected()) {
                Service.createPerson(txfLedsager[0].getText(), txfLedsager[1].getText(), txfLedsager[2].getText(),
                        txfLedsager[3].getText());
            }

            // TODO: Update LocalDate.now.
            Service.createTilmelding(konference, LocalDate.now(), LocalDate.now(), deltager, ledsager);

            hide();
        } else {
            confirm.hide();
        }
    }

    private void cboxTillaegAction() {
        lvwTillaeg.setDisable(false);
        if (!lvwTillaeg.getItems().contains(cboxTillaeg.getSelectionModel().getSelectedItem())) {
            lvwTillaeg.getItems().add(cboxTillaeg.getSelectionModel().getSelectedItem());
        }
        // cboxTillaeg.getSelectionModel().clearSelection(); // Causes problems?

        updateTotalPrice();
    }

    private void cboxUdflugterAction() {
        lvwUdflugter.setDisable(false);
        if (!lvwUdflugter.getItems().contains(cboxUdflugter.getSelectionModel().getSelectedItem())) {
            lvwUdflugter.getItems().add(cboxUdflugter.getSelectionModel().getSelectedItem());
        }

        updateTotalPrice();
    }

    private void updateTotalPrice() {
        double total = 0;

        if (cboxHotels.getSelectionModel().getSelectedItem() != null) {
            if (cbLedsager.isSelected()) {
                total += cboxHotels.getSelectionModel().getSelectedItem().getPrisDobbelt();
            } else {
                total += cboxHotels.getSelectionModel().getSelectedItem().getPrisEnkelt();
            }
            for (HotelTillaeg ht : lvwTillaeg.getItems()) {
                total += ht.getPris();
            }
        }
        for (Udflugt u : lvwUdflugter.getItems()) {
            total += u.getPris();
        }

        lblTotalPrice.setText("TOTAL: " + total);
    }

    private void btnCancelAction() {
        hide();
    }

}