# ledger-cordova-plugin
Cordova plugin that allows iOS and Android devices to interface with a Ledger cryptocurrency hardware wallet

## Installation
Using the command line utility and fetching from Github
```
cordova plugin add https://github.com/Guppster/ledger-cordova-plugin
```

or cloning and linking a local copy
```
git clone git@github.com:Guppster/ledger-cordova-plugin.git
cordova plugin add --link path/to/cloned/repository
```

## Functionality

### init()
Determines of the ledger device is pluged in and sets up the plugin to initiate further communication. 
Returns back success if device found, error if not found.

### setupWallet()

### getWalletPublicAddress()

### verifyPin()

### getPinRemainingAttempts()



