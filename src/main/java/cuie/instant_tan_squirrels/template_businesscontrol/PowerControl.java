package cuie.instant_tan_squirrels.template_businesscontrol;

import javafx.beans.property.*;
import javafx.css.PseudoClass;
import javafx.scene.control.Control;
import javafx.scene.control.Skin;
import javafx.scene.text.Font;

import java.util.regex.Pattern;

public class PowerControl extends Control {
    private static final PseudoClass MANDATORY_CLASS = PseudoClass.getPseudoClass("mandatory");
    private static final PseudoClass INVALID_CLASS = PseudoClass.getPseudoClass("invalid");

    static final String FORMATTED_DOUBLE_PATTERN = "%.2f";

    private static final String DOUBLE_REGEX = "[\\d']{1,14}(\\.\\d{1,2})?";
    private static final Pattern DOUBLE_PATTERN = Pattern.compile(DOUBLE_REGEX);

    private final DoubleProperty value = new SimpleDoubleProperty();
    private final StringProperty userFacingText = new SimpleStringProperty();

    private final BooleanProperty mandatory = new SimpleBooleanProperty() {
        @Override
        protected void invalidated() {
            pseudoClassStateChanged(MANDATORY_CLASS, get());
        }
    };

    private final BooleanProperty invalid = new SimpleBooleanProperty(false) {
        @Override
        protected void invalidated() {
            pseudoClassStateChanged(INVALID_CLASS, get());
        }
    };

    private final BooleanProperty readOnly = new SimpleBooleanProperty();
    private final StringProperty errorMessage = new SimpleStringProperty();


    public PowerControl() {
        initializeSelf();
        addValueChangeListener();
    }

    @Override
    protected Skin<?> createDefaultSkin() {
        return new PowerSkin(this);
    }

    public void reset() {
        setUserFacingText(String.valueOf(getValue()));
    }

    public void increase() {
        if (getValue() >= 100)
            setValue(getValue() + 100);
        else if (getValue() < 100 && getValue() >= 20)
            setValue(getValue() + 10);
        else if (getValue() < 20)
            setValue(getValue() + 1);
    }

    public void decrease() {
        if (getValue() >= 100)
            setValue(getValue() - 100);
        else if (getValue() < 100 && getValue() >= 20)
            setValue(getValue() - 10);
        else if (getValue() < 20 && getValue() > 0)
            setValue(getValue() - 1);
    }

    private void initializeSelf() {
        getStyleClass().add("business-control");

        setUserFacingText(String.valueOf(getValue()));
    }

    private void addValueChangeListener() {

        String pattern = DOUBLE_REGEX + "k";
        userFacingText.addListener((observable, oldValue, userInput) -> {

            if (userInput.matches(pattern)) {
                double thousands = Double.parseDouble(userInput.substring(0, userInput.length() - 1));
                setInvalid(false);
                setValue(thousands * 1000);
                setUserFacingText(String.valueOf(thousands * 1000));
            } else if (isMandatory() && (userInput == null || userInput.isEmpty())) {
                setInvalid(true);
                setErrorMessage("Mandatory Field");
                return;
            } else if (isDouble(userInput)) {
                setInvalid(false);
                setErrorMessage(null);
                setValue(convertToDouble(userInput));
            } else {
                setInvalid(true);
                setErrorMessage("Not a Double");
            }
        });

        valueProperty().addListener((observable, oldValue, newValue) -> {
            setInvalid(false);
            setErrorMessage(null);
            setUserFacingText(convertToString(newValue.doubleValue()));
        });
    }

    public void loadFonts(String... font) {
        for (String f : font) {
            Font.loadFont(getClass().getResourceAsStream(f), 0);
        }
    }

    public void addStylesheetFiles(String... stylesheetFile) {
        for (String file : stylesheetFile) {
            String stylesheet = getClass().getResource(file).toExternalForm();
            getStylesheets().add(stylesheet);
        }
    }

    private boolean isDouble(String userInput) {
        return DOUBLE_PATTERN.matcher(userInput).matches();
    }

    private double convertToDouble(String userInput) {
        return Double.parseDouble(userInput);
    }

    private String convertToString(double newValue) {
        return String.format(FORMATTED_DOUBLE_PATTERN, newValue);
    }


    // alle  Getter und Setter
    public double getValue() {
        return value.get();
    }

    public DoubleProperty valueProperty() {
        return value;
    }

    public void setValue(double value) {
        this.value.set(value);
    }

    public boolean isReadOnly() {
        return readOnly.get();
    }

    public BooleanProperty readOnlyProperty() {
        return readOnly;
    }

    public void setReadOnly(boolean readOnly) {
        this.readOnly.set(readOnly);
    }

    public boolean isMandatory() {
        return mandatory.get();
    }

    public BooleanProperty mandatoryProperty() {
        return mandatory;
    }

    public void setMandatory(boolean mandatory) {
        this.mandatory.set(mandatory);
    }

    public boolean getInvalid() {
        return invalid.get();
    }

    public BooleanProperty invalidProperty() {
        return invalid;
    }

    public void setInvalid(boolean invalid) {
        this.invalid.set(invalid);
    }

    public String getErrorMessage() {
        return errorMessage.get();
    }

    public StringProperty errorMessageProperty() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage.set(errorMessage);
    }

    public String getUserFacingText() {
        return userFacingText.get();
    }

    public StringProperty userFacingTextProperty() {
        return userFacingText;
    }

    public void setUserFacingText(String userFacingText) {
        this.userFacingText.set(userFacingText);
    }

    public boolean isInvalid() {
        return invalid.get();
    }


}
