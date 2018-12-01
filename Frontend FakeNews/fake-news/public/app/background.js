// Called when the user clicks on the browser action
/* global chrome */

chrome.browserAction.onClicked.addListener(function(activeTab) {
    var newURL = "http://twitter.com";
    chrome.tabs.create({ url: newURL });
});