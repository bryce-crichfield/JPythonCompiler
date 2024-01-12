package org.ksu.jpython;

import javafx.event.ActionEvent;
import javafx.stage.FileChooser;

import java.io.File;

public class EditorController {

    public void fileNew(ActionEvent actionEvent) {
        System.out.println("New");
    }

    public void fileOpen(ActionEvent actionEvent) {
        FileChooser dialog = new FileChooser();
        File file = dialog.showOpenDialog(null);
        if (file != null) {
            System.out.println(file.getAbsolutePath());
        }
    }

    public void fileSave(ActionEvent actionEvent) {
        FileChooser dialog = new FileChooser();
        File file = dialog.showSaveDialog(null);
        if (file != null) {
            System.out.println(file.getAbsolutePath());
        }
    }

    public void fileSaveAs(ActionEvent actionEvent) {
        FileChooser dialog = new FileChooser();
        File file = dialog.showSaveDialog(null);
        if (file != null) {
            System.out.println(file.getAbsolutePath());
        }
    }

    public void fileExit(ActionEvent actionEvent) {
        System.exit(0);
    }
}