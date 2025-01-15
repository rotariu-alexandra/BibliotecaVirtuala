package com.app.multistat.Vaadin;

import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

@Route("")
public class MainView extends VerticalLayout {

    public MainView() {
        createMenu();
        createContent();
    }

    private void createMenu() {
        Anchor cartiLink = new Anchor("carti", "Cărți");
        Anchor utilizatoriLink = new Anchor("utilizatori", "Utilizatori");
        Anchor recenziiLink = new Anchor("recenzii", "Recenzii");
        Anchor imprumuturiLink = new Anchor("imprumuturi", "Împrumuturi");

        HorizontalLayout menu = new HorizontalLayout(cartiLink, utilizatoriLink, recenziiLink, imprumuturiLink);
        menu.setPadding(true);
        menu.setSpacing(true);

        add(menu);
    }

    private void createContent() {
        H1 title = new H1("Bine ai venit la Biblioteca Virtuală!");
        add(title);
    }
}
