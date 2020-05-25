package cuie.instant_tan_squirrels.template_businesscontrol;

import javafx.scene.control.Label;
import javafx.scene.layout.VBox;


class DropDownChooser extends VBox {
    private static final String STYLE_CSS = "dropDownChooser.css";

    private final PowerControl powerControl;

    private Label tobeReplacedLabel;

    DropDownChooser(PowerControl powerControl) {
        this.powerControl = powerControl;
        initializeSelf();
        initializeParts();
        layoutParts();
        setupBindings();
    }

    private void initializeSelf() {
        getStyleClass().add("drop-down-chooser");

        String stylesheet = getClass().getResource(STYLE_CSS).toExternalForm();
        getStylesheets().add(stylesheet);
    }

    private void initializeParts() {
        tobeReplacedLabel = new Label("to be replaced");
    }

    private void layoutParts() {
        getChildren().addAll(tobeReplacedLabel);
    }

    private void setupBindings() {
    }
}
