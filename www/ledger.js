var exec = require('cordova/exec');

exports.init = function init (success, error) {
    exec(success, error, 'ledger', 'init');
};

exports.setupWallet = function setupWallet (success, error) {
    exec(success, error, 'ledger', 'setupWallet');
};

exports.getWalletPublicKey = function getWalletPublicAddress (success, error) {
    exec(success, error, 'ledger', 'getWalletPublicAddress);
};

exports.verifyPin = function verifyPin (success, error) {
    exec(success, error, 'ledger', 'verifyPin');
};

exports.getPinRemainingAttempts = function getPinRemainingAttempts (success, error) {
    exec(success, error, 'ledger', 'getPinRemainingAttempts', []);
};
