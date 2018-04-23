var exec = require('cordova/exec');

exports.init = function init (success, error) {
    exec(success, error, 'ledger', 'init', []);
};
