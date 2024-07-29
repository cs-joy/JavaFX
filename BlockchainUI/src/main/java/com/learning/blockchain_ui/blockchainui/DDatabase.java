package com.learning.blockchain_ui.blockchainui;

import io.reactivex.Flowable;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.core.methods.response.Log;
import org.web3j.protocol.core.methods.request.Transaction;
import org.web3j.protocol.core.methods.response.EthCall;
import org.web3j.protocol.core.methods.response.EthSendTransaction;
import org.web3j.tuples.generated.Tuple2;
import org.web3j.tx.Contract;
import org.web3j.tx.TransactionManager;
import org.web3j.tx.gas.ContractGasProvider;
import org.web3j.tx.gas.StaticGasProvider;
import org.web3j.abi.EventEncoder;
import org.web3j.abi.FunctionEncoder;
import org.web3j.abi.FunctionDecoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.abi.datatypes.Utf8String;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Bool;
import org.web3j.abi.EventValues;
import org.web3j.abi.datatypes.Event;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.core.methods.response.BaseEventResponse;

public class DDatabase extends Contract {
    public static final String BINARY = "<YOUR_CONTRACT_BIN>";

    public static final String FUNC_ADDDATA = "addData";
    public static final String FUNC_UPDATEDATA = "updateData";
    public static final String FUNC_DELETEDATA = "deleteData";
    public static final String FUNC_GETDATA = "getData";
    public static final String FUNC_GRANTPERMISSION = "grantPermission";
    public static final String FUNC_REVOKEPERMISSION = "revokePermission";

    protected DDatabase(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    public RemoteCall<TransactionReceipt> addData(BigInteger id, String content) {
        final Function function = new Function(
                FUNC_ADDDATA,
                Arrays.<Type>asList(new Uint256(id), new Utf8String(content)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<TransactionReceipt> updateData(BigInteger id, String content) {
        final Function function = new Function(
                FUNC_UPDATEDATA,
                Arrays.<Type>asList(new Uint256(id), new Utf8String(content)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<TransactionReceipt> deleteData(BigInteger id) {
        final Function function = new Function(
                FUNC_DELETEDATA,
                Arrays.<Type>asList(new Uint256(id)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<Tuple2<BigInteger, String>> getData(BigInteger id) {
        final Function function = new Function(FUNC_GETDATA,
                Arrays.<Type>asList(new Uint256(id)),
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}, new TypeReference<Utf8String>() {}));
        return new RemoteCall<Tuple2<BigInteger, String>>(new Callable<Tuple2<BigInteger, String>>() {
            @Override
            public Tuple2<BigInteger, String> call() throws Exception {
                List<Type> results = executeCallMultipleValueReturn(function);
                return new Tuple2<BigInteger, String>(
                        (BigInteger) results.get(0).getValue(),
                        (String) results.get(1).getValue());
            }
        });
    }

    public RemoteCall<TransactionReceipt> grantPermission(String user) {
        final Function function = new Function(
                FUNC_GRANTPERMISSION,
                Arrays.<Type>asList(new Address(user)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<TransactionReceipt> revokePermission(String user) {
        final Function function = new Function(
                FUNC_REVOKEPERMISSION,
                Arrays.<Type>asList(new Address(user)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public static DDatabase load(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return new DDatabase(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static RemoteCall<DDatabase> deploy(Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(DDatabase.class, web3j, credentials, contractGasProvider, BINARY, "");
    }
}

