define(function (require) {
    "use strict";
    var viewStyles = require("../views/view");
    var viewResolver = require("ViewResolver");

    return {
        init: function (rtr) {
            viewStyles.initPage();
            rtr.forward("#people");

        }
    };
});