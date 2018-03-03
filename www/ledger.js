var exec = require('cordova/exec');

exports.init = function (success, error) {
    exec(success, error, 'gurpreetsingh.ledger', 'init');
};

