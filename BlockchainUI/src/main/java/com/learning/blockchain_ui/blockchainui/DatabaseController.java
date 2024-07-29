package com.learning.blockchain_ui.blockchainui;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.http.HttpService;
import org.web3j.tuples.generated.Tuple2;
import org.web3j.tx.gas.DefaultGasProvider;
import org.web3j.crypto.Credentials;

import java.math.BigInteger;

public class DatabaseController {
    @FXML
    private TextField idField;
    @FXML
    private TextField contentField;
    @FXML
    private TextArea outputArea;

    private static final String BSC_TESTNET_URL = "https://data-seed-prebsc-1-s1.binance.org:8545/";
    private static final String CONTRACT_ADDRESS = "<YOUR_CONTRACT_ADDRESS>";
    private static final String PRIVATE_KEY = "<YOUR_PRIVATE_KEY>";

    private DecentralizedDatabase contract;

    @FXML
    public void initialize() {
        Web3j web3j = Web3j.build(new HttpService(BSC_TESTNET_URL));
        Credentials credentials = Credentials.create(PRIVATE_KEY);
        contract = DecentralizedDatabase.load(CONTRACT_ADDRESS, web3j, credentials, new DefaultGasProvider());
    }

    @FXML
    private void handleAddData() {
        try {
            BigInteger id = new BigInteger(idField.getText());
            String content = contentField.getText();
            TransactionReceipt receipt = contract.addData(id, content).send();
            outputArea.appendText("Data added: " + receipt.getTransactionHash() + "\n");
        } catch (Exception e) {
            outputArea.appendText("Error: " + e.getMessage() + "\n");
        }
    }

    @FXML
    private void handleUpdateData() {
        try {
            BigInteger id = new BigInteger(idField.getText());
            String content = contentField.getText();
            TransactionReceipt receipt = contract.updateData(id, content).send();
            outputArea.appendText("Data updated: " + receipt.getTransactionHash() + "\n");
        } catch (Exception e) {
            outputArea.appendText("Error: " + e.getMessage() + "\n");
        }
    }

    @FXML
    private void handleDeleteData() {
        try {
            BigInteger id = new BigInteger(idField.getText());
            TransactionReceipt receipt = contract.deleteData(id).send();
            outputArea.appendText("Data deleted: " + receipt.getTransactionHash() + "\n");
        } catch (Exception e) {
            outputArea.appendText("Error: " + e.getMessage() + "\n");
        }
    }

    @FXML
    private void handleGetData() {
        try {
            BigInteger id = new BigInteger(idField.getText());
            Tuple2<BigInteger, String> data = contract.getData(id).send();
            outputArea.appendText("Data: ID=" + data.getValue1() + ", Content=" + data.getValue2() + "\n");
        } catch (Exception e) {
            outputArea.appendText("Error: " + e.getMessage() + "\n");
        }
    }
}

