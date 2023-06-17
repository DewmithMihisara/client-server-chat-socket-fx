package org.example.controller;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class ServerFormController implements Initializable {
    @FXML
    private TextField msgFld;

    @FXML
    private Button sendBtn;

    @FXML
    private TextArea txtArea;
    ServerSocket serverSocket;
    Socket socket;
    DataInputStream dataInputStream;
    DataOutputStream dataOutputStream;

    String message;
    {
        message="";
    }
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
                serverSocket=new ServerSocket(4000);
                socket=serverSocket.accept();
                dataInputStream=new DataInputStream(socket.getInputStream());
                dataOutputStream=new DataOutputStream(socket.getOutputStream());
                while (!message.equals("exit")){
                    message=dataInputStream.readUTF();
                    txtArea.appendText("\nClient: "+message);
                }

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }).start();
    }
}
