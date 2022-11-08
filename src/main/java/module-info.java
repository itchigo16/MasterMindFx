module com.brunycharotte.mastermindfx {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.brunycharotte.mastermindfx to javafx.fxml;
    exports com.brunycharotte.mastermindfx;
}