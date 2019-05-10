

function AppModel(_reflesh) {
	var self = this;
	self.pageSize = ko.observable(coinManagerPageSize);
	self.users = ko.observableArray([]);
	self.phone = ko.observable("");
	self.pageIdx = ko.observable(1);
	self.reflesh = _reflesh;


	self.setData = function(data) {
		self.users.removeAll();
		for ( var i in data) {
			var item = $.grep(data[i].list, function(o) {
				return o.data === "#SYS_coin";
			})[0];
			console.log(item);
			var u = new User(data[i],item);
			self.users.push(u);
		}
	};
	
	self.tryReflesh = function(){
		self.reflesh();
	};
	
	self.updateAmount = function(uid,d){
		var match = ko.utils.arrayFirst(self.users(), function(item) {
		    return item.uid === uid;
		});
		match.updateAmount(d);
	};
	
	self.isFirstPage = function(){
		return self.pageIdx() == 1;
	}
	
	self.isLastPage = function(){
		return self.users().length<self.pageSize();
	};
	
	self.plusPage = function(i){
		i = parseInt(i);
		self.pageIdx(self.pageIdx()+i);
	};
	
	self.pageIdx.subscribe(function(newValue) {
		self.reflesh();
	});
	
	self.phone.subscribe(function(newValue) {
		self.pageIdx(1);
		//self.reflesh();
	});
	
	
}