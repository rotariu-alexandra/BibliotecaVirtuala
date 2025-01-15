package com.app.multistat.Vaadin;

import com.app.multistat.entity.Utilizator;
import com.app.multistat.service.UtilizatorService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Route("utilizatori")
public class UtilizatoriView extends VerticalLayout {

    private final UtilizatorService utilizatorService;
    private Grid<Utilizator> grid;
    private List<Utilizator> totiUtilizatorii;

    @Autowired
    public UtilizatoriView(UtilizatorService utilizatorService) {
        this.utilizatorService = utilizatorService;

        // Bara de meniu
        createMenu();

        // Titlu
        add(new H1("Gestionare Utilizatori"));

        // Căutare utilizatori
        TextField searchField = new TextField("Caută Utilizator");
        searchField.setPlaceholder("Introduceți numele utilizatorului...");
        Button searchButton = new Button("Caută", e -> performSearch(searchField.getValue()));

        // Layout pentru căutare
        HorizontalLayout searchLayout = new HorizontalLayout(searchField, searchButton);

        // Grid pentru afișarea utilizatorilor
        grid = new Grid<>(Utilizator.class);
        grid.setColumns("id", "nume", "email");
        refreshGrid();

        // Butoane de acțiune
        Button addButton = new Button("Adaugă Utilizator", e -> showAddDialog());
        Button editButton = new Button("Editează Utilizator", e -> showEditDialog(grid.asSingleSelect().getValue()));
        Button deleteButton = new Button("Șterge Utilizator", e -> deleteUtilizator(grid.asSingleSelect().getValue()));

        // Layout pentru acțiuni
        HorizontalLayout actions = new HorizontalLayout(addButton, editButton, deleteButton);

        // Adăugăm componentele
        add(searchLayout, actions, grid);
    }

    private void createMenu() {
        Anchor homeLink = new Anchor("/", "Acasă");
        Anchor cartiLink = new Anchor("carti", "Cărți");
        Anchor imprumuturiLink = new Anchor("imprumuturi", "Împrumuturi");
        Anchor recenziiLink = new Anchor("recenzii", "Recenzii");


        HorizontalLayout menu = new HorizontalLayout(homeLink, cartiLink, imprumuturiLink, recenziiLink);
        menu.setPadding(true);
        menu.setSpacing(true);
        menu.getStyle().set("background-color", "#f0f0f0").set("padding", "10px");

        add(menu);
    }

    private void refreshGrid() {
        totiUtilizatorii = utilizatorService.obtineUtilizatori();
        grid.setItems(totiUtilizatorii);
    }

    private void performSearch(String searchTerm) {
        if (searchTerm == null || searchTerm.isEmpty()) {
            Notification.show("Introduceți un termen de căutare!");
            refreshGrid();
            return;
        }

        List<Utilizator> rezultate = totiUtilizatorii.stream()
                .filter(utilizator -> utilizator.getNume().toLowerCase().contains(searchTerm.toLowerCase()))
                .toList();

        if (rezultate.isEmpty()) {
            Notification.show("Niciun utilizator găsit pentru termenul de căutare: " + searchTerm);
        } else {
            grid.setItems(rezultate);
        }
    }

    private void showAddDialog() {
        Dialog dialog = new Dialog();
        FormLayout form = new FormLayout();

        TextField numeField = new TextField("Nume");
        EmailField emailField = new EmailField("Email");

        form.add(numeField, emailField);

        Button saveButton = new Button("Salvează", e -> {
            if (numeField.isEmpty() || emailField.isEmpty()) {
                Notification.show("Toate câmpurile sunt obligatorii!");
                return;
            }

            Utilizator utilizator = new Utilizator();
            utilizator.setNume(numeField.getValue());
            utilizator.setEmail(emailField.getValue());


        });

        Button cancelButton = new Button("Anulează", e -> dialog.close());

        HorizontalLayout buttons = new HorizontalLayout(saveButton, cancelButton);
        dialog.add(form, buttons);
        dialog.open();
    }

    private void showEditDialog(Utilizator utilizator) {
        if (utilizator == null) {
            Notification.show("Selectați un utilizator pentru editare!");
            return;
        }

        Dialog dialog = new Dialog();
        FormLayout form = new FormLayout();

        TextField numeField = new TextField("Nume");
        numeField.setValue(utilizator.getNume());
        EmailField emailField = new EmailField("Email");
        emailField.setValue(utilizator.getEmail());

        form.add(numeField, emailField);

        Button saveButton = new Button("Salvează", e -> {
            if (numeField.isEmpty() || emailField.isEmpty()) {
                Notification.show("Toate câmpurile sunt obligatorii!");
                return;
            }

            utilizator.setNume(numeField.getValue());
            utilizator.setEmail(emailField.getValue());

            utilizatorService.editeazaUtilizator(utilizator.getId(), utilizator);
            refreshGrid();
            dialog.close();
            Notification.show("Utilizator modificat cu succes!");
        });

        Button cancelButton = new Button("Anulează", e -> dialog.close());

        HorizontalLayout buttons = new HorizontalLayout(saveButton, cancelButton);
        dialog.add(form, buttons);
        dialog.open();
    }

    private void deleteUtilizator(Utilizator utilizator) {
        if (utilizator == null) {
            Notification.show("Selectați un utilizator pentru ștergere!");
            return;
        }

        utilizatorService.stergeUtilizator(utilizator.getId());
        refreshGrid();
        Notification.show("Utilizator șters cu succes!");
    }
}
