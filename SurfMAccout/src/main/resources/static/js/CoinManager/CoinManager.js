//初始化網頁上的元件
$(function() {
	var appModel = new AppModel(reflesh);

	ko.applyBindings(appModel);

	$(".preloader").fadeOut();
	// Navbar
	$(".navbar.navbar-default.navbar-static-top.m-b-0").load("_navbar.html");

	// Sidebar
	$(".navbar-default.sidebar").load("_sidebar.html");

	// Plus amount window
	$("#pointDialog").dialog(
			{
				autoOpen : false,
				height : 250,
				width : 350,
				modal : true,
				buttons : {
					'Save' : function() {
						PlusAmount($(this).data('uid'), $('#amount').val(), $(
								'#note').val());
						$(this).dialog('close');
					},
					'Cancel' : function() {
						$(this).dialog('close');
					}
				},
				close : function() {
					$('#amount').val('0');
				}
			});



	reflesh();
	
	function reflesh(){
		$.ajax({
			method : 'get',
			url : '/admin/item/query?phone='+appModel.phone()+'&page='+appModel.pageIdx()+'&size='+appModel.pageSize(),
			data : {},
			cache : false,
			dataType : 'json',
			success : function(data) {
				// var msg= JSON.stringify(data);
				// console.log(msg);

				appModel.setData(data);

			},
			error : function(err, status) {
				alert('Error Message: ' + err);
			}
		});
	}
	
	
	// 在點數操作視窗下,按下 SAVE 時觸發
	function PlusAmount(uid, amount, note) {

		$.ajax({
			method : 'post',
			url : '/admin/item/24f8359b62ab0cc600317a6c973644d53ceb1e1b/user/'
					+ uid + '/plus?amount=' + amount,
			data : JSON.stringify({
				'custom' : note
			}),
			cache : false,
			contentType : "application/json; charset=utf-8",
			dataType : 'json',
			success : function(data) {
				console.log(data);
				appModel.updateAmount(uid,data);
					//alert("Save succeed! "+data.amount)
		
			},
			error : function(err, status) {
				alert('Error Message: ' + err);
			}
		});
	}

});

// 按下功能扭(+-)時彈出 Popup 視窗
$(document).on('click', 'button[class="openPlusForm"]', function() {
	var uid = $(this).attr('title');
	$('#pointDialog').data('uid', uid).dialog('open');
});



