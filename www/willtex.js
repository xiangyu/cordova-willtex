var cordova = require('cordova');

function Clipboard () {}

Clipboard.prototype.sendData = function (text, onSuccess, onFail) {
	cordova.exec(onSuccess, onFail, "WillTex", "sendData", [text]);
};

Clipboard.prototype.saveImage = function (filename, onSuccess, onFail) {
	cordova.exec(onSuccess, onFail, "WillTex", "saveImage", [filename]);
};

var clipboard = new Clipboard();
module.exports = clipboard;