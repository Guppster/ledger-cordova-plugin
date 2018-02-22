var exec = require('cordova/exec');

exports.init = function (arg0, success, error) {
    exec(success, error, 'ledger', 'init', [arg0]);
};

exports.setupWallet = function (arg0, success, error) {
  exec(success, error, 'ledger', 'setup', [arg0]);
};

exports.verifyPin = function (arg0, success, error) {
  exec(success, error, 'ledger', 'verifyPin', [arg0]);
};
