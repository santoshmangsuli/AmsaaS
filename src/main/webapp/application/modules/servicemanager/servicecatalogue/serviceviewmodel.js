define(function (require) {

    ko.validation.registerExtenders();

    var modelView = function (data) {
        this.dataModel = {
            srvcCode: ko.observable(data.srvcCode).extend({
                required: true
            }),
            srvcName: ko.observable(data.srvcName),
            srvcDescription: ko.observable(data.srvcDescription)
        };

        this.viewModel = {
            isIdEditable: ko.observable(false),
            isEditable: ko.observable(false),
            isSelected: ko.observable(false)
        };

    };
    return modelView;

});