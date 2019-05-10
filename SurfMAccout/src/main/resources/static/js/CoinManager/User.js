function User(d,it) {
    var self = this;
    self.uid = d.user.uid;
    self.data = ko.observable(d);
    self.item = ko.observable(it);
    
    self.updateAmount = function(i){
    	 self.item(i);
    } 
}