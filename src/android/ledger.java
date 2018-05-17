package me.gurpreetsingh.cordova;

import com.btchip.BTChipDongle.BTChipPublicKey;
import com.btchip.BTChipException;
import com.btchip.comm.BTChipTransport;
import com.btchip.comm.android.BTChipTransportAndroid;
import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;
import org.json.JSONArray;
import org.json.JSONException;
import android.content.Context;
import android.widget.Toast;
import com.btchip.*;

/**
 * This class handles the native Android operations of interacting with a Ledger device
 * It provides an API for JS to call native methods using the execute method as a link
 */
public class ledger extends CordovaPlugin
{
    private static final String DUMMY_PIN = "0000";
    private static final int SW_INVALID_PIN = 0x63C0;
    private static final int SW_CONDITIONS_NOT_SATISFIED = 0x6985;

    private BTChipTransportAndroid device;
    private Context context;

    private Toast toastSuccess = Toast.makeText(context, "success", Toast.LENGTH_LONG);
    private Toast toastError = Toast.makeText(context, "error", Toast.LENGTH_LONG);
    private BTChipDongle dongle;

    @Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
        //TODO: When cleaning up use reflection to get the method
        //Don't mess with it until functionality works
        //method = this.getClass().getMethod(action, JSONArray.class, CallbackContext.class);
        //method.invoke(args, callbackContext)

        //Temporary for debugging
        context = this.cordova.getActivity();

        Toast bad = Toast.makeText(context, "i hate js", Toast.LENGTH_LONG);
        bad.show();

        if(action.equals("init"))
        {
            init(callbackContext);
        }
        else if(action.equals("setupWallet"))
        {
            setupWallet(callbackContext);
        }
        else if(action.equals("getWalletPublicKey"))
        {
            getWalletPublicKey(callbackContext);
        }
        else if(action.equals("verifyPin"))
        {
            verifyPin(callbackContext);
        }
        else if(action.equals("getPinRemainingAttempts"))
        {
            getPinRemainingAttempts(callbackContext);
        }

        return true;
    }

    //Finds the USB devices and attempts to connect to it, return back if found or not
    private void init(CallbackContext callbackContext)
    {
        Toast toastFound = Toast.makeText(context, "device found", Toast.LENGTH_LONG);
        Toast toastNotFound = Toast.makeText(context, "device NOT found", Toast.LENGTH_LONG);

        toastNotFound.show();

        //Create an interface to the device
        device = new BTChipTransportAndroid(context);

        if(device.isPluggedIn())
        {
            toastFound.show();
        }
        else
        {
            toastNotFound.show();
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

        BTChipTransport transport = device.getTransport();
        dongle = new BTChipDongle(transport);
    }

    //Allows the creation of a new wallet
    private void setupWallet(CallbackContext callbackContext)
    {
        try
        {
            boolean setupResult = dongle.setup(new BTChipDongle.OperationMode[]{BTChipDongle.OperationMode.WALLET},
                    new BTChipDongle.Feature[]{BTChipDongle.Feature.RFC6979},
                    03,         //Standard address header goes here
                    05,    //multisig address header goes here
                    new byte[4],
                    null,
                    null,
                    null,
                    null);

            if(setupResult)
            {
                callbackContext.success("Wallet successfully setup");
            }
            else
            {
                callbackContext.error("Error setting up wallet");
            }
        }
        catch (BTChipException e)
        {
            callbackContext.error("Error setting up wallet" + e.toString());
        }
    }

    //Returns the public key associated with the dongle
    private void getWalletPublicKey(CallbackContext callbackContext)
    {
        try
        {
            //TODO: Figure out how parameters work in cordova and get a real key string
            BTChipPublicKey publicKey = dongle.getWalletPublicKey("some key path");

            callbackContext.success(publicKey.toString());
        }
        catch (BTChipException e)
        {
            if (e.getSW() == SW_CONDITIONS_NOT_SATISFIED)
            {
                callbackContext.error("Wallet not setup");
                //We can set one up on the fly (setupWallet()) if needed
            }
        }
    }

    //Prompts for pin verification
    private void verifyPin(CallbackContext callbackContext)
    {
        try
        {
            dongle.verifyPin(DUMMY_PIN.getBytes());
            callbackContext.success("PIN Valid");
        }
        catch (BTChipException e)
        {
            if ((e.getSW() & 0xfff0) == SW_INVALID_PIN)
            {
                callbackContext.error("Invalid PIN - " + (e.getSW() - SW_INVALID_PIN) + " attempts remaining");
            }
        }

    }

    //Returns how many pin attempts are remaining
    private void getPinRemainingAttempts(CallbackContext callbackContext)
    {
        try
        {
            callbackContext.success(dongle.getVerifyPinRemainingAttempts());
        }
        catch (BTChipException e)
        {
            e.printStackTrace();
        }
    }

}
