package org.example.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.io.*;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;

public class ClientFormController implements Initializable {
    @FXML
    private TextField msgFld;
    @FXML
    private Button sendBtn;

    @FXML
    private TextArea txtArea;
    String message;
    {
        message="";
    }
    Socket socket;
    DataOutputStream dataOutputStream;
    DataInputStream dataInputStream;
    @FXML
    void msgFldOnAction(ActionEvent event) {
        sendBtn.fire();
    }

    @FXML
    void sendBtnOnAction(ActionEvent event) throws IOException {
        dataOutputStream.writeUTF(msgFld.getText().trim());
        dataOutputStream.flush();
        txtArea.appendText("\nMe: "+msgFld.getText());
        msgFld.clear();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        new Thread(() -> {
            try {

                socket=new Socket("localhost",4000);
                dataInputStream=new DataInputStream(socket.getInputStream());
                dataOutputStream=new DataOutputStream(socket.getOutputStream());
                message = "";
                while (!message.equals("exit")){
                    message=dataInputStream.readUTF();
                    txtArea.appendText("\nServer: "+message);
                }

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }).start();
    }
}
