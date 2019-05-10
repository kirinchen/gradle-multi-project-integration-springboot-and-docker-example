function Row(n,v) {
    var self = this;
    self.name = ko.observable(n);
    self.value = ko.observable(v);
    
}