module com.magicgui.magicthegathering {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;


    opens com.magicgui.magicthegathering to javafx.fxml;
    exports com.magicgui.magicthegathering;
}