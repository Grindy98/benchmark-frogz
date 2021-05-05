module test {
    requires javafx.controls;
    requires javafx.fxml;

    opens gui to javafx.fxml;
    opens gui.controllers to javafx.fxml;

    //exports gui;
    exports gui.controllers;
    //exports test.log;
    exports test.time;
    exports test.implementation;
    exports test.benchmark;
    exports test.benchmark.cpu;
    exports gui.main to javafx.graphics;
}