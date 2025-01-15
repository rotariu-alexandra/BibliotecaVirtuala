package com.app.multistat.Vaadin;

import com.app.multistat.entity.Carte;
import com.app.multistat.entity.Imprumut;
import com.app.multistat.entity.Utilizator;
import com.app.multistat.service.CarteService;
import com.app.multistat.service.ImprumutService;
import com.app.multistat.service.UtilizatorService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.util.List;

@Route("imprumuturi")
public class ImprumuturiView extends VerticalLayout {

    private final ImprumutService imprumutService;
    private final UtilizatorService utilizatorService;
    private final CarteService carteService;

    private Grid<Imprumut> grid;

    @Autowired
    public ImprumuturiView(ImprumutService imprumutService, UtilizatorService utilizatorService, CarteService carteService) {
        this.imprumutService = imprumutService;
        this.utilizatorService = utilizatorService;
        this.carteService = carteService;

        // Bara de meniu
        createMenu();

        // Titlu
        add(new H1("Imprumuturi"));

        // Grid pentru afișarea împrumuturilor existente
        grid = new Grid<>(Imprumut.class);
        grid.setColumns("id", "utilizator.nume", "carte.titlu", "dataImprumut", "dataReturnare", "returnat");
        refreshGrid();

        // Buton pentru adăugarea unui nou împrumut
        Button addButton = new Button("Adaugă Împrumut", e -> showAddDialog());
        add(addButton, grid);
    }

    private void createMenu() {
        Anchor homeLink = new Anchor("/", "Acasă");
        Anchor cartiLink = new Anchor("carti", "Cărți");
        Anchor recenziiLink = new Anchor("recenzii", "Recenzii");
        Anchor utilizatoriLink = new Anchor("utilizatori", "Utilizatori");

        HorizontalLayout menu = new HorizontalLayout(homeLink, cartiLink,  recenziiLink, utilizatoriLink);
        menu.setPadding(true);
        menu.setSpacing(true);
        menu.getStyle().set("background-color", "#f0f0f0").set("padding", "10px");

        add(menu);
    }

    private void refreshGrid() {
        List<Imprumut> imprumuturi = imprumutService.obtineImprumuturi();
        grid.setItems(imprumuturi);
    }

    private void showAddDialog() {
        Dialog dialog = new Dialog();
        FormLayout form = new FormLayout();

        // ComboBox pentru selectarea utilizatorului
        ComboBox<Utilizator> utilizatorComboBox = new ComboBox<>("Selectează Utilizator");
        utilizatorComboBox.setItems(utilizatorService.obtineUtilizatori());
        utilizatorComboBox.setItemLabelGenerator(Utilizator::getNume);

        // ComboBox pentru selectarea cărții
        ComboBox<Carte> carteComboBox = new ComboBox<>("Selectează Carte");
        carteComboBox.setItems(carteService.obtineCarti());
        carteComboBox.setItemLabelGenerator(Carte::getTitlu);

        // Data împrumutului și data returnării
        DatePicker dataImprumut = new DatePicker("Data Împrumut");
        dataImprumut.setValue(LocalDate.now());
        DatePicker dataReturnare = new DatePicker("Data Returnare");

        form.add(utilizatorComboBox, carteComboBox, dataImprumut, dataReturnare);

        // Butoane pentru salvare și anulare
        Button saveButton = new Button("Salvează", e -> {
            if (utilizatorComboBox.isEmpty() || carteComboBox.isEmpty() || dataImprumut.isEmpty() || dataReturnare.isEmpty()) {
                Notification.show("Toate câmpurile sunt obligatorii!");
                return;
            }

            // Crearea unui nou împrumut
            Imprumut imprumut = new Imprumut();
            imprumut.setUtilizator(utilizatorComboBox.getValue());
            imprumut.setCarte(carteComboBox.getValue());
            imprumut.setDataImprumut(java.sql.Date.valueOf(dataImprumut.getValue()));
            imprumut.setDataReturnare(java.sql.Date.valueOf(dataReturnare.getValue()));
            imprumut.setReturnat(false);

            imprumutService.adaugaImprumut(imprumut);
            refreshGrid();
            dialog.close();
            Notification.show("Împrumut adăugat cu succes!");
        });

        Button cancelButton = new Button("Anulează", e -> dialog.close());

        HorizontalLayout buttons = new HorizontalLayout(saveButton, cancelButton);
        dialog.add(form, buttons);
        dialog.open();
    }
}
