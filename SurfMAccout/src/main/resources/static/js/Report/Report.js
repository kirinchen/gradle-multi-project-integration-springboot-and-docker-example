var dateFormat = function() {
	var token = /d{1,4}|m{1,4}|yy(?:yy)?|([HhMsTt])\1?|[LloSZ]|"[^"]*"|'[^']*'/g, timezone = /\b(?:[PMCEA][SDP]T|(?:Pacific|Mountain|Central|Eastern|Atlantic) (?:Standard|Daylight|Prevailing) Time|(?:GMT|UTC)(?:[-+]\d{4})?)\b/g, timezoneClip = /[^-+\dA-Z]/g, pad = function(
			val, len) {
		val = String(val);
		len = len || 2;
		while (val.length < len)
			val = "0" + val;
		return val;
	};

	// Regexes and supporting functions are cached through closure
	return function(date, mask, utc) {
		var dF = dateFormat;

		// You can't provide utc if you skip other args (use the "UTC:" mask
		// prefix)
		if (arguments.length == 1
				&& Object.prototype.toString.call(date) == "[object String]"
				&& !/\d/.test(date)) {
			mask = date;
			date = undefined;
		}

		// Passing date through Date applies Date.parse, if necessary
		date = date ? new Date(date) : new Date;
		if (isNaN(date))
			throw SyntaxError("invalid date");

		mask = String(dF.masks[mask] || mask || dF.masks["default"]);

		// Allow setting the utc argument via the mask
		if (mask.slice(0, 4) == "UTC:") {
			mask = mask.slice(4);
			utc = true;
		}

		var _ = utc ? "getUTC" : "get", d = date[_ + "Date"](), D = date[_
				+ "Day"](), m = date[_ + "Month"](), y = date[_ + "FullYear"](), H = date[_
				+ "Hours"](), M = date[_ + "Minutes"](), s = date[_ + "Seconds"]
				(), L = date[_ + "Milliseconds"](), o = utc ? 0 : date
				.getTimezoneOffset(), flags = {
			d : d,
			dd : pad(d),
			ddd : dF.i18n.dayNames[D],
			dddd : dF.i18n.dayNames[D + 7],
			m : m + 1,
			mm : pad(m + 1),
			mmm : dF.i18n.monthNames[m],
			mmmm : dF.i18n.monthNames[m + 12],
			yy : String(y).slice(2),
			yyyy : y,
			h : H % 12 || 12,
			hh : pad(H % 12 || 12),
			H : H,
			HH : pad(H),
			M : M,
			MM : pad(M),
			s : s,
			ss : pad(s),
			l : pad(L, 3),
			L : pad(L > 99 ? Math.round(L / 10) : L),
			t : H < 12 ? "a" : "p",
			tt : H < 12 ? "am" : "pm",
			T : H < 12 ? "A" : "P",
			TT : H < 12 ? "AM" : "PM",
			Z : utc ? "UTC" : (String(date).match(timezone) || [ "" ]).pop()
					.replace(timezoneClip, ""),
			o : (o > 0 ? "-" : "+")
					+ pad(
							Math.floor(Math.abs(o) / 60) * 100 + Math.abs(o)
									% 60, 4),
			S : [ "th", "st", "nd", "rd" ][d % 10 > 3 ? 0
					: (d % 100 - d % 10 != 10) * d % 10]
		};

		return mask.replace(token, function($0) {
			return $0 in flags ? flags[$0] : $0.slice(1, $0.length - 1);
		});
	};
}();

// Some common format strings
dateFormat.masks = {
	"default" : "ddd mmm dd yyyy HH:MM:ss",
	shortDate : "m/d/yy",
	mediumDate : "mmm d, yyyy",
	longDate : "mmmm d, yyyy",
	fullDate : "dddd, mmmm d, yyyy",
	shortTime : "h:MM TT",
	mediumTime : "h:MM:ss TT",
	longTime : "h:MM:ss TT Z",
	isoDate : "yyyy-mm-dd",
	isoTime : "HH:MM:ss",
	isoDateTime : "yyyy-mm-dd'T'HH:MM:ss",
	isoUtcDateTime : "UTC:yyyy-mm-dd'T'HH:MM:ss'Z'"
};

// Internationalization strings
dateFormat.i18n = {
	dayNames : [ "Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sunday",
			"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday" ],
	monthNames : [ "Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug",
			"Sep", "Oct", "Nov", "Dec", "January", "February", "March",
			"April", "May", "June", "July", "August", "September", "October",
			"November", "December" ]
};

// For convenience...
Date.prototype.format = function(mask, utc) {
	return dateFormat(this, mask, utc);
};


String.prototype.format = function (args) {
	var str = this;
	return str.replace(String.prototype.format.regex, function(item) {
		var intVal = parseInt(item.substring(1, item.length - 1));
		var replace;
		if (intVal >= 0) {
			replace = args[intVal];
		} else if (intVal === -1) {
			replace = "{";
		} else if (intVal === -2) {
			replace = "}";
		} else {
			replace = "";
		}
		return replace;
	});
};
String.prototype.format.regex = new RegExp("{-?[0-9]+}", "g");

$(function() {

	var reportModel = new ReportModel(reflesh);

	ko.applyBindings(reportModel);

	$(".navbar.navbar-default.navbar-static-top.m-b-0").load("_navbar.html");
	$(".navbar-default.sidebar").load("_sidebar.html");

	$("#startDate").datepicker({
		defaultDate : "+1w",
		changeMonth : true,
		numberOfMonths : 2,
		dateFormat : "yy-mm-dd",
		showAnim : "blind"
	}).on("change", function() {
		$("#endDate").datepicker("option", "minDate", GetDate(this));
	})

	$("#endDate").datepicker({
		defaultDate : "+1w",
		changeMonth : true,
		numberOfMonths : 2,
		dateFormat : "yy-mm-dd",
		showAnim : "blind"
	}).on("change", function() {
		$("#startDate").datepicker("option", "maxDate", GetDate(this));
	})

	reflesh();
	
	function reflesh(){
		var rang= reportModel.getDateRange();
		GetGroupCount(rang.start, rang.end);
	}
	

	// 取得報表 API
	function GetGroupCount(start, end) {

		$(".preloader").fadeIn();

		$("#startDate").datepicker('setDate', start);
		$("#endDate").datepicker('setDate', end);

		var startISO = start.format("yyyy-mm-dd");
		var endISO = end.format("yyyy-mm-dd");

		var _url = '/admin/item/24f8359b62ab0cc600317a6c973644d53ceb1e1b/group/count?start='
				+ startISO + '&end=' + endISO;

		reportModel.removeAllRows();
		fetchData(_url);
		
		_url = externalFetchReportUrl.format([startISO, endISO]);
		
		fetchData(_url);
	}
	
	function fetchData(_url){
		console.log("_url=" + _url);
		$.ajax({
			method : 'get',
			url : _url,
			data : {},
			cache : false,
			dataType : 'json',
			success : function(data) {
				$(".preloader").fadeOut();
				console.log("data=" + data);
				if (data) {
					reportModel.addData(data);
				} else {
					alert("Something error!")
				}
			},
			error : function(err, status) {
				$(".preloader").fadeOut();
				alert('Error Message: ' + err);
			}
		});
	}

	function GetDate(element) {
		var date;
		try {
			date = $.datepicker.parseDate("yy-mm-dd", element.value);
		} catch (error) {
			date = null;
		}
		return date;
	}

	// 查詢指定日期區間的報表
	$(document).on('click', 'button[class="btnQuery"]', function() {
		var start = new Date($("#startDate").val());
		var end = new Date($("#endDate").val());

		GetGroupCount(start, end)
	});
	
	$("#previousBtn").click(function() {
		reportModel.movePage(-1);
		reflesh();
	});
	
	$("#nextBtn").click(function() {
		reportModel.movePage(1);
		reflesh();
	});

});
