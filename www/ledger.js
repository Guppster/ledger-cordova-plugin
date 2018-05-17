var exec = cordova.require('cordova/exec');

var ledgerPlugin = function() {
    console.log('ledgerPlugin instanced');
};

// An example on how to expose methods
//  LedgerPlugin.prototype.example = function example (option1, option2, success, error) {
//      var options = {};
//      options.option1 = option1;
//      options.option2 = option2;
//      cordova.exec(success, error, 'ledger', 'example', [optiens]);
//  };

ledgerPlugin.prototype.init = function (success, error) {
    exec(success, error, 'ledger', 'init', []);
};

ledgerPlugin.prototype.setupWallet = function (success, error) {
    exec(success, error, 'ledger', 'setupWallet', []);
};

ledgerPlugin.prototype.getWalletPublicKey = function (success, error) {
    exec(success, error, 'ledger', 'getWalletPublicAddress', []);
};

ledgerPlugin.prototype.verifyPin = function (success, error) {
    exec(success, error, 'ledger', 'verifyPin', []);
};

ledgerPlugin.prototype.getPinRemainingAttempts = function (success, error) {
    exec(success, error, 'ledger', 'getPinRemainingAttempts', []);
};

if (typeof module != 'undefined' && module.exports) {
    module.exports = ledgerPlugin;
}
