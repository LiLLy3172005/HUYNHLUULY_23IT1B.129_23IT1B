module com.example.qlnn1 {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.microsoft.sqlserver.jdbc;
    requires java.sql;
    requires  java.naming;
    opens com.example.qlnn1 to javafx.fxml;
    exports com.example.qlnn1;
}