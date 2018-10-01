package com.krotos.views;

import com.krotos.CalcAdapter;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;


public class Controller {
    private CalcAdapter calcAdapter=new CalcAdapter();

    @FXML
    private TextArea textArea;

    @FXML
    private Button calculateButton;

    @FXML
    private Label label;

    @FXML
    private Label secondLabel;

    @FXML
    void onClickCalculateButton(ActionEvent event) {
        String result=calcAdapter.calculate(textArea.getText());
        label.setText(result);
        System.out.println(result);
    }


}
