function ReportModel(_rangeTypeChanged) {
	var self = this;

	self.rows = ko.observableArray([]);
	self.rangeType = ko.observable("weak");
	self.pageIdx = ko.observable(0);
	self.rangeTypeChanged = _rangeTypeChanged;

	self.removeAllRows = function() {
		self.rows.removeAll();
	};

	self.addData = function(datas) {
		for ( var propertyName in datas) {
			var v = datas[propertyName];
			var r = new Row(propertyName, v);
			self.rows.push(r);
		}
	};

	self.getDateRange = function() {

		switch (self.rangeType()) {
		case 'weak':
			return {
				'start' : Date.today().addWeeks(self.pageIdx())
						.previous().sunday(),
				'end' : Date.today().addWeeks(self.pageIdx())
						.saturday()
			};
		case 'month':
			return {
				'start' : Date.today().addMonths(self.pageIdx()).moveToFirstDayOfMonth(),
				'end' : Date.today().addMonths(self.pageIdx()).moveToLastDayOfMonth()
			};

		}

	};

	self.movePage = function(count) {
		self.pageIdx(self.pageIdx() + count);
	};

	self.rangeType.subscribe(function(newValue) {
		// alert(newValue);
		self.pageIdx(0);
		self.rangeTypeChanged();
	});

}