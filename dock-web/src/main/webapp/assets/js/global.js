(function($) {
	$.extend({
		msNull: function(val) {
			if (val === null || typeof(val) === 'undefined') {
				return "";
			}
			return val;
		},
		isEmpty: function(val) {
			if (val === null || typeof(val) === 'undefined' || val === '') {
				return true;
			}
			return false;
		},
		isNotEmpty: function(val) {
			return !$.isEmpty(val);
		},
		
		setToken: function(token) {
			return $.localStorage.set("token", token);
		},
		getToken: function() {
			return $.msNull($.localStorage.get("token"));
		},
		mDate: function(data, format) {
			if (data) {
				return moment(data).format(format ? format : "YYYY-MM-DD");
			} else {
				return moment().format("YYYY-MM-DD");
			}
		},
		mTime: function(data, format) {
			if (data) {
				return moment(data).format(format ? format : "YYYY-MM-DD HH:mm:ss");
			} else {
				return moment().format("YYYY-MM-DD HH:mm:ss");
			}
		},
		mAlert: function (options) {
			bootbox.alert({
				title: options.title || '提示',
	            message: options.message || '提示信息为空'
	        });
		},
		mAjax: function(req, fnCallback) {
			$.ajax({
				"cache": false,
				"async" : req.async && true,
				"type": req.method || "POST",
				"url": req.url,
				"contentType": req.contentType || "application/json;charset=UTF-8",
				"dataType": req.dataType || "json",
				"data": req.param || '{"time":"' + new Date().getTime() + '"}',
				"headers" : { "Authorization": "Bearer " + $.getToken() },
				"success": function (json) {
					if (json.success === "T") {
						fnCallback(json);
					} else {
						$.mAlert({ message: json.message || json.sub_message });
					}
				},
                "error": function (response) {
                    console.log(response);
                }
			});
			return {};
		},
		handlebars: function(source, data) {
			if (source instanceof jQuery) {
				source = $(source).html();
			}
			var template = Handlebars.compile(source);
			return template(data);
		},
		datalink: function() {
			var datalink_ = $('#navbar_home li[data-rel]').attr('data-rel');
			if (datalink_){
				$('#sidebar-menu li[data-link="'+datalink_+'"]').addClass('active');
				$('#sidebar-menu li[data-link="'+datalink_+'"]').parent(".submenu").parent("li").addClass('open active');
			}
		},
		loadmenu: function(load) {
			var menukey = "tjcloud_menu_key";
			var storage = $.localStorage;
			if (load || storage.isEmpty(menukey)) {
				$.mAjax({url: '/v3/menus/tree', param: JSON.stringify({ tenantId: 0 })}, function(json) {
					var jsonData = { elements: json.data };
					$('#sidebar-menu').html($.handlebars($('#menu-hdbars'), jsonData));
					
					$.datalink();

					storage.set(menukey, jsonData);
				});
			} else {
				var data = storage.get(menukey);
				$('#sidebar-menu').html($.handlebars($('#menu-hdbars'), data));
				
				$.datalink();
			}
		},
		clearStorage: function() {
			$.localStorage.remove("tjcloud_menu_key");
		},
		setStorage: function(key, value) {
			$.localStorage.set(key, value);
		},
		setStorage: function(key, value) {
			$.localStorage.get(key);
		},
		removeStorage: function(key) {
			$.localStorage.remove(key);
		},
		clearAll: function() {
			$.localStorage.removeAll();
		}
	});
	$.fn.extend({
		pagination: function(options) {
			if ($.isEmpty(options.url)) {
				$.malert({ message: '请求地址为空' });
				return null;
			}

			var columnDefs = options.columnDefs || [];
			if (options.status) {
				columnDefs.push({
			    	width: 70,
			    	targets: -2,
			    	render: function (data, type, row, meta) {
			    		return $.handlebars($('#status-hdbars'), data);
			    	}
				});
			}
			if (options.last) {
				columnDefs.push({
			    	width: 140,
			    	targets: -1,
			    	render: function (data, type, row, meta) {
			    		return $.handlebars($('#button-hdbars'), {elements: [
			    			    {id: data.id, type: "info", name: "btn-view", icon: "search", title: "查看"},
			    			    {id: data.id, type: "success", name: "btn-edit", icon: "edit", title: "编辑"},
			    			    {id: data.id, type: "warning", name: "btn-del", icon: "remove", title: "删除"}]});
			    	}
			    });
			}
			
			var _self = this;
			var _parameters = {
				"bPaginate": options.paginate || false,
				"bLengthChange": false,
				"bFilter": false,
				"bSort": options.sort || false,
				"bInfo": options.info || false,
				/*"bStateSave": true,*/
				"bServerSide": true,
				
				"sPaginationType": "bootstrap",
				"sAjaxSource": options.url + '?rand=' + Math.random(),
				"sAjaxDataProp": options.paginate ? "dataList" : "data",
				"language": {
					"url": "/assets/js/datatable/dataTables.zh_CN.json",
				},
				"columns": options.columns,
				"columnDefs": columnDefs.length > 0 ? columnDefs : {},
				"fnServerData": function (sSource, aoData, fnCallback) {
					var param = options.paremeters || {};
					param.search = aoData;
					
					$.mAjax({url: sSource, param: JSON.stringify(param)}, function(json) {
						if (options.paginate) {
							fnCallback(json.data);
						} else {
							fnCallback(json);
						}
					});
				},
				"bRetrieve": options.retrieve || false,
				"bDestroy": options.destory || false
			};
			
			if (options.paginate) {
				_parameters.sDom = "Tflt<'row DTTTFooter'<'col-sm-4 no-padding'i><'col-sm-8 no-padding'p>>";
			}
			if (options.scrollY) {
				_parameters.scrollY = options.scrollY;
			}
			
			var table = $(_self).dataTable(_parameters);
			return table;
		},
		handlebars: function (data) {
			if (source instanceof jQuery) {
				source = $(source).html();
			}
			var template = Handlebars.compile(source);
			this.html(template(data));
		},
		serializeObject : function() {
		    var obj = {};
		    var array = this.serializeArray();
		    $.each(array, function() {
		        if (obj[this.name] !== undefined) {
		            if (!obj[this.name].push) {
		                obj[this.name] = [obj[this.name]];
		            }
		            obj[this.name].push(this.value || '');
		        } else {
		            obj[this.name] = this.value || '';
		        }
		    });
		    return obj;
		},
		DSTree: function (req) {
			var _self = this;
			
			var DataSourceTree = function(options) {
				this._url = options.url;
			}
            DataSourceTree.prototype = {
            	data: function (options, callback) {
            		var parent_id = "";
	                if (!("name" in options) && !("type" in options)) {
	                    //load the first level
	                	if (!req.lazy) {
		                    $.mAjax({url: this._url, param: JSON.stringify({ parentId: '' })}, function(json) {
		                		callback({ data: json.data });
		                		
		                		_self.find(".tree-folder-header:visible").click();
		        			});
	                	}
	                } else if ("type" in options && options.type == "folder") {
	                    if ("additionalParameters" in options && "children" in options.additionalParameters) {
	                    	parent_id = options.additionalParameters["id"];
	                    	callback({ data: options.additionalParameters.children });
	                    }
	                } else {} //no data
	                
	                if (req.lazy) {
	                    $.mAjax({url: this._url, param: JSON.stringify({ parentId: parent_id })}, function(json) {
	                		callback({ data: json.data });
	        			});
                	}
	            }
            };
            
            _self.tree({
	            dataSource: new DataSourceTree({ url: req.url }),
	            loadingHTML: '<div class="tree-loading"><i class="icon-refresh icon-spin blue"></i></div>',
	            selectable: req.selectable || false,
	            multiSelect: req.multiSelect || false
	        });
		}
	});
})(jQuery);

/**$(function(){	
	$.loadmenu();
});*/