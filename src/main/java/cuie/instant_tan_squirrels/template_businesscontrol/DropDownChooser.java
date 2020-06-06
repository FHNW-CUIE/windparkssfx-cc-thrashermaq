package cuie.instant_tan_squirrels.template_businesscontrol;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Slider;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.transform.Affine;
import javafx.util.Duration;


class DropDownChooser extends VBox {
    private static final String STYLE_CSS = "dropDownChooser.css";

    private final PowerControl powerControl;

    private Canvas canvas;
    private double degrees;
    private Slider slider;
    private Timeline timeline;

    DropDownChooser(PowerControl powerControl) {
        this.powerControl = powerControl;
        initializeSelf();
        initializeParts();
        layoutParts();
        setupBindings();
        setupValueChangedListeners();
    }

    private void initializeSelf() {
        getStyleClass().add("drop-down-chooser");

        String stylesheet = getClass().getResource(STYLE_CSS).toExternalForm();
        getStylesheets().add(stylesheet);
    }

    private void initializeParts() {
        canvas = new Canvas(400, 400);
        slider = new Slider(0, 50000, 0);
        slider.getStylesheets().add(getClass().getResource("dropdownchooser.css").toExternalForm());
        createAnimation();
    }

    private void createAnimation() {
        if (timeline != null) {
            timeline.stop();
        }
        timeline = new Timeline(getKeyFrame());
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }

    private KeyFrame getKeyFrame() {
        double millis = 100000 / powerControl.getValue();
        if (millis > 100) millis = 100;
        System.out.println(millis);
        return new KeyFrame(Duration.millis(millis), ev -> {
            draw();
        });
    }

    private void layoutParts() {
        getChildren().addAll(canvas, slider);
    }

    private void setupBindings() {
        slider.valueProperty().bindBidirectional(powerControl.valueProperty());
    }

    private void setupValueChangedListeners() {
        powerControl.valueProperty().addListener((observable, oldValue, newValue) -> {
            createAnimation();
        });
    }

    private void draw() {
        GraphicsContext graphicsContext = canvas.getGraphicsContext2D();

        degrees += 0.5;
        if (degrees >= 360) degrees = 0;

        Affine rotate = new Affine();
        rotate.appendRotation(degrees, canvas.getWidth() / 2, canvas.getHeight() / 2);
        graphicsContext.setTransform(rotate);

        graphicsContext.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());

        // use svg path
        graphicsContext.setFill(Color.web("#4D75FF"));
        graphicsContext.beginPath();
        graphicsContext.fillOval(175, 175, 50, 50);

        graphicsContext.appendSVGPath("M147.849369,177.450354 C149.598009,176.440775 169.419313,181.032696 170.512972,182.926969 C171.60663,184.821241 165.893637,194.563401 167.156025,202.40162 C167.926297,207.184271 169.870171,212.023465 170.999824,215.17035 L171.005099,218.913384 C140.96118,230.020353 29.0659049,261.314839 18.9685562,261.273158 C16.8650915,261.264475 13.1506058,259.818692 11.9598068,255.906229 C11.1256741,253.165621 11.6316229,251.000945 14.9186928,247.967015 C75.4003672,213.047904 119.413603,191.454829 147.849369,177.450354 Z");

        graphicsContext.appendSVGPath("M237.45161,12.5855918 C238.510862,10.7682794 241.62019,8.27433197 245.603882,9.1993014 C248.394385,9.84722538 250.016075,11.3677277 251,15.7313786 C251,85.5696006 247.693534,134.482719 245.603882,166.111052 C245.603882,168.130208 231.716511,183 229.529193,183 C227.341876,183 221.761415,173.181323 214.342124,170.355474 C206.922832,167.529624 197.7959,167.35775 198.003477,166.111052 C203.09883,135.508617 232.32974,21.3729612 237.45161,12.5855918 Z");

        graphicsContext.appendSVGPath("M218.501851,227.242906 C224.658754,222.230537 229.371068,214.412319 230.346951,215.215435 C254.301761,234.929357 338.530684,317.311895 343.579833,326.14125 C344.624046,327.967246 345.229204,331.906976 342.436311,334.89447 C340.479941,336.987154 338.352302,337.631327 334.081307,336.301606 C273.599633,301.382495 232.892863,274.062452 206.546749,256.438595 C204.798108,255.429017 198.864177,235.967304 199.957835,234.073031 C201.051494,232.178759 212.344948,232.255276 218.501851,227.242906 Z");

        graphicsContext.stroke();
        graphicsContext.fill();
    }
}
