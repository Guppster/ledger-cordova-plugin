package me.gurpreetsingh.ledger;

import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CallbackContext;

import com.btchip.*;
import com.btchip.comm.BTChipTransport;

import org.json.JSONArray;
import org.json.JSONException;

/**
 * This class handles the native Android operations of interacting with a Ledger device
 * It provides an API for JS to call native methods using the execute method as a link
 */
public class ledger extends CordovaPlugin {

    @Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
        //TODO: When cleaning up use reflection to get the method
        //Don't mess with it until functionality works
        //method = this.getClass().getMethod(action, JSONArray.class, CallbackContext.class);
        //method.invoke(args, callbackContext)

        //Temporary for debugging
        switch(action)
        {
            case "initTransport":
                this.initTransport(args, callbackContext);
                return true;
            case "initDongle":
                this.initDongle(args, callbackContext);
                return true;
            case "setup":
                this.setup(args, callbackContext);
                return true;
            case "getPinRemainingAttempts":
                this.getPinRemainingAttempts(args, callbackContext);
                return true;
            case "verifyPin":
                this.verifyPin(args, callbackContext);
                return true;
            case "getWalletPublicKey":
                this.getWalletPublicKey(args, callbackContext);
                return true;
        }

        return false;
    }

    //Sets up the TEE transport allowing connection to the device
    //TODO: Link initialization steps because state needs to be maintained within Java (i think)
    private void initTransport(JSONArray args, CallbackContext callbackContext)
    {

        //Success can take a JSONObject, JSONArray, or String
        //Obviously can't send back a Transport object b/c it's going to JS
        callbackContext.success("Transport initialized");
        callbackContext.error("Cannot initialize transport");
    }

    //Uses the TEE transport to create a BTDongle object
    //TODO: Link initialization steps because state needs to be maintained within Java (i think)
    private void initDongle(JSONArray args, CallbackContext callbackContext)
    {
        BTChipDongle dongle = new BTChipDongle(new BTChipTransport()
        {
            @Override
            public byte[] exchange(byte[] command) throws BTChipException
            {
                return new byte[0];
            }

            @Override
            public void close() throws BTChipException
            {

            }

            @Override
            public void setDebug(boolean debugFlag)
            {

            }
        });
    }

    //Allows the creation of a new wallet
    private void setup(JSONArray args, CallbackContext callbackContext)
    {
    }

    //Returns the public key associated with the dongle
    private void getWalletPublicKey(JSONArray args, CallbackContext callbackContext)
    {
    }

    //Prompts for pin verification
    private void verifyPin(JSONArray args, CallbackContext callbackContext)
    {
    }

    //Returns how many pin attempts are remaining
    private void getPinRemainingAttempts(JSONArray args, CallbackContext callbackContext)
    {
    }

}
