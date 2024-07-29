package com.learning.blockchain_ui.blockchainui;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.gas.ContractGasProvider;
import org.web3j.tx.gas.DefaultGasProvider;
//import org.web3j.utils.Numeric;
import org.web3j.protocol.core.methods.response.Web3ClientVersion;
import org.web3j.utils.Numeric;


import java.awt.*;
import java.net.URI;
import java.util.concurrent.CompletableFuture;

public class DecentralizedDatabaseController {

    private static final String CONTRACT_ADDRESS = "0x2D024540B74887F16956F7c12810Fb0a13886950";
    private static final String BSC_TESTNET_URL = "https://data-seed-prebsc-1-s1.binance.org:8545/";

    private Web3j web3j;
    private Credentials credentials;
    private ContractGasProvider gasProvider;
    private DecentralizedDatabase contract;

    @FXML
    private TextField keyField;

    @FXML
    private TextArea contentArea;

    @FXML
    private TextField userAddressField;

    @FXML
    private ComboBox<String> permissionTypeComboBox;

    @FXML
    private TextArea resultArea;

    @FXML
    private Label walletStatusLabel;

    @FXML
    public void initialize() {
        try {
            web3j = Web3j.build(new HttpService(BSC_TESTNET_URL));
            gasProvider = new DefaultGasProvider();
            Web3ClientVersion web3ClientVersion = web3j.web3ClientVersion().send();
            String clientVersion = web3ClientVersion.getWeb3ClientVersion();
            showResult("Connected to Ethereum client version: " + clientVersion);
        } catch (Exception e) {
            showResult("Initialization error: " + e.getMessage());
        }
    }

    @FXML
    private void connectMetaMask() {
        try {
            String url = "https://metamask.app.link/dapp/YOUR_DAPP_URL";
            Desktop.getDesktop().browse(new URI(url));
            showResult("Please connect your MetaMask wallet. Check your browser for the MetaMask popup.");

            // This is a placeholder. In a real-world scenario, you'd implement a callback mechanism
            // to receive the connected address from MetaMask.
            CompletableFuture.runAsync(() -> {
                try {
                    Thread.sleep(10000); // Wait for 10 seconds
                    javafx.application.Platform.runLater(() -> {
                        walletStatusLabel.setText("Connected");
                        showResult("MetaMask connected successfully!");
                    });
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        } catch (Exception e) {
            showResult("Error connecting to MetaMask: " + e.getMessage());
        }
    }

    @FXML
    private void createData() {
        try {
            checkWalletConnection();
            String key = keyField.getText();
            String content = contentArea.getText();
            byte[] keyBytes = Numeric.hexStringToByteArray(Numeric.toHexString(key.getBytes()));
            contract.create(keyBytes, content).send();
            showResult("Data created successfully.");
        } catch (Exception e) {
            showResult("Error creating data: " + e.getMessage());
        }
    }

    @FXML
    private void readData() {
        try {
            checkWalletConnection();
            String key = keyField.getText();
            byte[] keyBytes = Numeric.hexStringToByteArray(Numeric.toHexString(key.getBytes()));
            String content = contract.read(keyBytes).send();
            contentArea.setText(content);
            showResult("Data read successfully.");
        } catch (Exception e) {
            showResult("Error reading data: " + e.getMessage());
        }
    }

    @FXML
    private void updateData() {
        try {
            checkWalletConnection();
            String key = keyField.getText();
            String content = contentArea.getText();
            byte[] keyBytes = Numeric.hexStringToByteArray(Numeric.toHexString(key.getBytes()));
            contract.update(keyBytes, content).send();
            showResult("Data updated successfully.");
        } catch (Exception e) {
            showResult("Error updating data: " + e.getMessage());
        }
    }

    @FXML
    private void deleteData() {
        try {
            checkWalletConnection();
            String key = keyField.getText();
            byte[] keyBytes = Numeric.hexStringToByteArray(Numeric.toHexString(key.getBytes()));
            contract.remove(keyBytes).send();
            showResult("Data deleted successfully.");
        } catch (Exception e) {
            showResult("Error deleting data: " + e.getMessage());
        }
    }

    @FXML
    private void grantPermission() {
        try {
            checkWalletConnection();
            String userAddress = userAddressField.getText();
            String permissionType = permissionTypeComboBox.getValue().toLowerCase();

            if (permissionType.equals("create")) {
                contract.grantCreatePermission(userAddress).send();
                showResult("Create permission granted to " + userAddress);
            } else if (permissionType.equals("read")) {
                contract.grantReadPermission(userAddress).send();
                showResult("Read permission granted to " + userAddress);
            }
        } catch (Exception e) {
            showResult("Error granting permission: " + e.getMessage());
        }
    }

    @FXML
    private void revokePermission() {
        try {
            checkWalletConnection();
            String userAddress = userAddressField.getText();
            String permissionType = permissionTypeComboBox.getValue().toLowerCase();

            if (permissionType.equals("create")) {
                contract.revokeCreatePermission(userAddress).send();
                showResult("Create permission revoked from " + userAddress);
            } else if (permissionType.equals("read")) {
                contract.revokeReadPermission(userAddress).send();
                showResult("Read permission revoked from " + userAddress);
            }
        } catch (Exception e) {
            showResult("Error revoking permission: " + e.getMessage());
        }
    }

    private void showResult(String message) {
        resultArea.setText(message);
    }

    private void checkWalletConnection() throws Exception {
        if (credentials == null) {
            throw new Exception("MetaMask wallet is not connected. Please connect your wallet first.");
        }
    }
}