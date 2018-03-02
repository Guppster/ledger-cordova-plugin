package me.gurpreetsingh.ledger;

import com.btchip.comm.android.BTChipTransportAndroid;
import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;
import org.json.JSONArray;
import org.json.JSONException;

/**
 * This class handles the native Android operations of interacting with a Ledger device
 * It provides an API for JS to call native methods using the execute method as a link
 */
public class ledger extends CordovaPlugin
{
    private BTChipTransportAndroid device;

    @Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
        //TODO: When cleaning up use reflection to get the method
        //Don't mess with it until functionality works
        //method = this.getClass().getMethod(action, JSONArray.class, CallbackContext.class);
        //method.invoke(args, callbackContext)

        //Temporary for debugging
        switch(action)
        {
            case "init":
                this.init(args, callbackContext);
                return true;
            case "setupWallet":
                this.setupWallet(args, callbackContext);
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

    //Finds the USB devices and attempts to connect to it, return back if found or not
    private void init(JSONArray args, CallbackContext callbackContext)
    {
        //Create an interface to the device
        device = new BTChipTransportAndroid(cordova.getContext());

        //Parse the callback from connect method into the CallbackContext cordova wants
        boolean connectionResult = device.connect(cordova.getContext(), success ->
        {
            if(success)
            {
                callbackContext.success("Connection established");
            }
            else
            {
                callbackContext.error("Connection failed or timed out");
            }
        });
    }

    //Allows the creation of a new wallet
    private void setupWallet(JSONArray args, CallbackContext callbackContext)
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
