package com.app.multistat.Vaadin;

import com.app.multistat.entity.Carte;
import com.app.multistat.entity.CategorieCarte;
import com.app.multistat.entity.DetaliiCarte;
import com.app.multistat.service.CarteService;
import com.app.multistat.service.CategorieCarteService;
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
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Route("carti")
public class CartiView extends VerticalLayout {

    private final CarteService carteService;
    private final CategorieCarteService categorieCarteService;
    private Grid<Carte> grid;

    @Autowired
    public CartiView(CarteService carteService, CategorieCarteService categorieCarteService) {
        this.carteService = carteService;
        this.categorieCarteService = categorieCarteService;

        // Adăugăm meniul
        createMenu();

        // Setăm titlul paginii
        add(new H1("Lista de Cărți"));

        // Creăm gridul
        grid = new Grid<>(Carte.class);
        grid.setColumns("id", "titlu", "domeniu");

        // Adăugăm coloane personalizate pentru relații
        grid.addColumn(carte -> carte.getCategorie() != null ? carte.getCategorie().getName() : "N/A")
                .setHeader("Categorie");
        grid.addColumn(carte -> carte.getDetaliiCarte() != null ? carte.getDetaliiCarte().getStatusCarte() : "N/A")
                .setHeader("Status Carte");

        // Setăm datele din service
        refreshGrid();

        // Butoane de acțiune
        Button addButton = new Button("Adaugă Carte", e -> showAddDialog());
        Button editButton = new Button("Editează Carte", e -> showEditDialog(grid.asSingleSelect().getValue()));
        Button deleteButton = new Button("Șterge Carte", e -> deleteCarte(grid.asSingleSelect().getValue()));

        // Layout pentru butoane
        HorizontalLayout actions = new HorizontalLayout(addButton, editButton, deleteButton);

        // Adăugăm componentele în layout
        add(actions, grid);
    }

    private void createMenu() {
        Anchor homeLink = new Anchor("/", "Acasă");
        Anchor utilizatoriLink = new Anchor("utilizatori", "Utilizatori");
        Anchor recenziiLink = new Anchor("recenzii", "Recenzii");
        Anchor imprumuturiLink = new Anchor("imprumuturi", "Împrumuturi");

        HorizontalLayout menu = new HorizontalLayout(homeLink,  utilizatoriLink, recenziiLink, imprumuturiLink);
        menu.setPadding(true);
        menu.setSpacing(true);
        menu.getStyle().set("background-color", "#f0f0f0").set("padding", "10px");

        add(menu);
    }

    private void refreshGrid() {
        List<Carte> carti = carteService.obtineCarti();
        grid.setItems(carti);
    }


    private void showAddDialog() {
        Dialog dialog = new Dialog();
        FormLayout form = new FormLayout();

        TextField titluField = new TextField("Titlu");
        TextField domeniuField = new TextField("Domeniu");

        ComboBox<CategorieCarte> categorieComboBox = new ComboBox<>("Categorie");
        categorieComboBox.setItems(categorieCarteService.obtineCategorii());
        categorieComboBox.setItemLabelGenerator(CategorieCarte::getName);

        ComboBox<String> statusComboBox = new ComboBox<>("Status Carte");
        statusComboBox.setItems("Disponibilă", "Împrumutată");

        form.add(titluField, domeniuField, categorieComboBox, statusComboBox);

        Button saveButton = new Button("Salvează", e -> {
            confirmAction("Sigur doriți să salvați această carte?", () -> {
                DetaliiCarte detaliiCarte = new DetaliiCarte();
                detaliiCarte.setStatusCarte(statusComboBox.getValue());

                Carte carte = new Carte();
                carte.setTitlu(titluField.getValue());
                carte.setDomeniu(domeniuField.getValue());
                carte.setCategorie(categorieComboBox.getValue());
                carte.setDetaliiCarte(detaliiCarte);

                carteService.adaugaCarte(carte);
                refreshGrid();
                dialog.close();
                Notification.show("Carte adăugată cu succes!");
            });
        });

        Button cancelButton = new Button("Anulează", e -> {
            confirmAction("Sigur doriți să anulați?", dialog::close);
        });

        HorizontalLayout buttons = new HorizontalLayout(saveButton, cancelButton);
        dialog.add(form, buttons);
        dialog.open();
    }

    private void showEditDialog(Carte carte) {
        if (carte == null) {
            Notification.show("Selectați o carte pentru editare!");
            return;
        }

        Dialog dialog = new Dialog();
        FormLayout form = new FormLayout();

        TextField titluField = new TextField("Titlu");
        titluField.setValue(carte.getTitlu());
        TextField domeniuField = new TextField("Domeniu");
        domeniuField.setValue(carte.getDomeniu());

        ComboBox<CategorieCarte> categorieComboBox = new ComboBox<>("Categorie");
        categorieComboBox.setItems(categorieCarteService.obtineCategorii());
        categorieComboBox.setItemLabelGenerator(CategorieCarte::getName);
        categorieComboBox.setValue(carte.getCategorie());

        ComboBox<String> statusComboBox = new ComboBox<>("Status Carte");
        statusComboBox.setItems("Disponibilă", "Împrumutată");
        statusComboBox.setValue(carte.getDetaliiCarte() != null ? carte.getDetaliiCarte().getStatusCarte() : null);

        form.add(titluField, domeniuField, categorieComboBox, statusComboBox);

        Button saveButton = new Button("Salvează", e -> {
            confirmAction("Sigur doriți să salvați modificările?", () -> {
                carte.setTitlu(titluField.getValue());
                carte.setDomeniu(domeniuField.getValue());
                carte.setCategorie(categorieComboBox.getValue());
                if (carte.getDetaliiCarte() == null) {
                    carte.setDetaliiCarte(new DetaliiCarte());
                }
                carte.getDetaliiCarte().setStatusCarte(statusComboBox.getValue());

                carteService.adaugaCarte(carte);
                refreshGrid();
                dialog.close();
                Notification.show("Carte modificată cu succes!");
            });
        });

        Button cancelButton = new Button("Anulează", e -> {
            confirmAction("Sigur doriți să anulați?", dialog::close);
        });

        HorizontalLayout buttons = new HorizontalLayout(saveButton, cancelButton);
        dialog.add(form, buttons);
        dialog.open();
    }

    private void confirmAction(String message, Runnable action) {
        Dialog confirmDialog = new Dialog();
        confirmDialog.add(new H1(message));

        Button yesButton = new Button("Da", e -> {
            action.run();
            confirmDialog.close();
        });

        Button noButton = new Button("Nu", e -> confirmDialog.close());

        HorizontalLayout buttons = new HorizontalLayout(yesButton, noButton);
        confirmDialog.add(buttons);
        confirmDialog.open();
    }

    private void deleteCarte(Carte carte) {
        if (carte == null) {
            Notification.show("Selectați o carte pentru ștergere!");
            return;
        }

        confirmAction("Sigur doriți să ștergeți această carte?", () -> {
            carteService.stergeCarte(carte.getId());
            refreshGrid();
            Notification.show("Carte ștearsă cu succes!");
        });
    }
}
