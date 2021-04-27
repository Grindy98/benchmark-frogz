module test {
    requires javafx.controls;
    requires javafx.fxml;

    opens gui to javafx.fxml;
    opens gui.scenes to javafx.fxml;

    exports gui;
    exports gui.scenes;
    exports test.log;
    exports test.time;
    exports test.implementation;
    exports test.benchmark;
    exports test.benchmark.cpu;
}