package com.app.multistat.Vaadin;

import com.app.multistat.entity.Carte;
import com.app.multistat.entity.RecenzieCarte;
import com.app.multistat.entity.Utilizator;
import com.app.multistat.service.CarteService;
import com.app.multistat.service.RecenzieCarteService;
import com.app.multistat.service.UtilizatorService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Route("recenzii")
public class RecenzieCarteView extends VerticalLayout {

    private final RecenzieCarteService recenzieCarteService;
    private final CarteService carteService;
    private final UtilizatorService utilizatorService;

    private Grid<RecenzieCarte> grid;

    @Autowired
    public RecenzieCarteView(RecenzieCarteService recenzieCarteService, CarteService carteService, UtilizatorService utilizatorService) {
        this.recenzieCarteService = recenzieCarteService;
        this.carteService = carteService;
        this.utilizatorService = utilizatorService;

        // Bara de meniu
        createMenu();

        // Titlu
        add(new H1("Gestionare Recenzii"));

        // Grid pentru afișarea recenziilor
        grid = new Grid<>(RecenzieCarte.class, false); // Dezactivăm generarea automată a coloanelor
        grid.addColumn(recenzie -> recenzie.getCarte().getTitlu()).setHeader("Carte");
        grid.addColumn(recenzie -> recenzie.getUtilizator().getNume()).setHeader("Utilizator");
        grid.addColumn(RecenzieCarte::getRating).setHeader("Rating");
        grid.addColumn(RecenzieCarte::getComentariu).setHeader("Comentariu");
        refreshGrid();

        // Butoane de acțiune
        Button addButton = new Button("Adaugă Recenzie", e -> showAddDialog());
        Button deleteButton = new Button("Șterge Recenzie", e -> deleteRecenzie(grid.asSingleSelect().getValue()));

        // Layout pentru acțiuni
        HorizontalLayout actions = new HorizontalLayout(addButton, deleteButton);

        // Adăugăm componentele
        add(actions, grid);
    }

    private void createMenu() {
        Anchor homeLink = new Anchor("/", "Acasă");
        Anchor cartiLink = new Anchor("carti", "Cărți");
        Anchor imprumuturiLink = new Anchor("imprumuturi", "Împrumuturi");
        Anchor utilizatoriLink = new Anchor("utilizatori", "Utilizatori");

        HorizontalLayout menu = new HorizontalLayout(homeLink, cartiLink, imprumuturiLink, utilizatoriLink);
        menu.setPadding(true);
        menu.setSpacing(true);
        menu.getStyle().set("background-color", "#f0f0f0").set("padding", "10px");

        add(menu);
    }

    private void refreshGrid() {
        List<RecenzieCarte> recenzii = recenzieCarteService.obtineRecenzii();
        grid.setItems(recenzii);
    }

    private void showAddDialog() {
        Dialog dialog = new Dialog();
        FormLayout form = new FormLayout();

        // ComboBox pentru selectarea cărții
        ComboBox<Carte> carteComboBox = new ComboBox<>("Selectează Carte");
        carteComboBox.setItems(carteService.obtineCarti());
        carteComboBox.setItemLabelGenerator(Carte::getTitlu);

        // ComboBox pentru selectarea utilizatorului
        ComboBox<Utilizator> utilizatorComboBox = new ComboBox<>("Selectează Utilizator");
        utilizatorComboBox.setItems(utilizatorService.obtineUtilizatori());
        utilizatorComboBox.setItemLabelGenerator(Utilizator::getNume);

        // TextField pentru rating
        TextField ratingField = new TextField("Rating (1-5)");
        ratingField.setPlaceholder("Introduceți un număr între 1 și 5");

        // TextArea pentru comentariu
        TextArea comentariuField = new TextArea("Comentariu");
        comentariuField.setPlaceholder("Scrieți un comentariu...");

        form.add(carteComboBox, utilizatorComboBox, ratingField, comentariuField);

        // Butoane pentru salvare și anulare
        Button saveButton = new Button("Salvează", e -> {
            if (carteComboBox.isEmpty() || utilizatorComboBox.isEmpty() || ratingField.isEmpty() || comentariuField.isEmpty()) {
                Notification.show("Toate câmpurile sunt obligatorii!");
                return;
            }

            try {
                int rating = Integer.parseInt(ratingField.getValue());
                if (rating < 1 || rating > 5) {
                    Notification.show("Rating-ul trebuie să fie între 1 și 5!");
                    return;
                }

                RecenzieCarte recenzie = new RecenzieCarte();
                recenzie.setCarte(carteComboBox.getValue());
                recenzie.setUtilizator(utilizatorComboBox.getValue());
                recenzie.setRating(rating);
                recenzie.setComentariu(comentariuField.getValue());

                recenzieCarteService.adaugaRecenzie(recenzie);
                refreshGrid();
                dialog.close();
                Notification.show("Recenzie adăugată cu succes!");
            } catch (NumberFormatException ex) {
                Notification.show("Rating-ul trebuie să fie un număr între 1 și 5!");
            }
        });

        Button cancelButton = new Button("Anulează", e -> dialog.close());

        HorizontalLayout buttons = new HorizontalLayout(saveButton, cancelButton);
        dialog.add(form, buttons);
        dialog.open();
    }

    private void deleteRecenzie(RecenzieCarte recenzie) {
        if (recenzie == null) {
            Notification.show("Selectați o recenzie pentru ștergere!");
            return;
        }

        recenzieCarteService.stergeRecenzie(recenzie.getId());
        refreshGrid();
        Notification.show("Recenzie ștearsă cu succes!");
    }
}
