package me.gurpreetsingh.cordova;

import com.btchip.comm.android.BTChipTransportAndroid;
import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.PluginResult;
import org.json.JSONArray;
import org.json.JSONException;
import android.content.Context;
import android.widget.Toast;

/**
 * This class handles the native Android operations of interacting with a Ledger device
 * It provides an API for JS to call native methods using the execute method as a link
 */
public class ledger extends CordovaPlugin
{
    private BTChipTransportAndroid device;
    private Context context;

    @Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
        //TODO: When cleaning up use reflection to get the method
        //Don't mess with it until functionality works
        //method = this.getClass().getMethod(action, JSONArray.class, CallbackContext.class);
        //method.invoke(args, callbackContext)

        //Temporary for debugging
        context = this.cordova.getActivity().getApplicationContext();

        if(action.equals("init"))
        {
            init(callbackContext);
        }

        return true;
    }

    //Finds the USB devices and attempts to connect to it, return back if found or not
    private void init(CallbackContext callbackContext)
    {
        Toast toastFound = Toast.makeText(context, "device found", Toast.LENGTH_LONG);
        Toast toastSuccess = Toast.makeText(context, "success", Toast.LENGTH_LONG);
        Toast toastError = Toast.makeText(context, "error", Toast.LENGTH_LONG);

        //Create an interface to the device
        device = new BTChipTransportAndroid(context);

        if(device.isPluggedIn())
        {
            toastFound.show();
        }

        //Parse the callback from connect method into the CallbackContext cordova wants
        boolean connectionResult = device.connect(context, success ->
        {
            if(success)
            {
                toastSuccess.show();
                callbackContext.success("Connection established");
            }
            else
            {
                toastError.show();
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
