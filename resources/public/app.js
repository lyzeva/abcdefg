Ext.application({
	name : 'HelloExt',
	launch : function() {
		Ext.create('Ext.container.Viewport', {
			layout : 'border',
			items : [{
						xtype : "component",
						region : "north"
					},
					{
						region : "west",
						width : "20%",
						title : 'Configuration',
						xtype: "panel",
						frame:true,
						autoScroll:true,
						items:[
						       {
						    	   xtype:"label",
						    	   text:"Choose Metric Name"
						       },
						       {
						    	   xtype:"textfield",
						    	   id:"chooseMetricName",
						    	   width:300,
						    	   value:"CPU/User Time"
						       },{
                                   xtype:"label",
                                   text:"Choose TopN Correlation Series"
                               },
                               {
                                   xtype:"textfield",
                                   id:"chooseTopN",
                                   width:300,
                                   value:"5"
                               },{
						    	   xtype:"label",
						    	   text:"MySQL IP Address"
						       },
						       {
						    	   xtype:"textfield",
						    	   id:"mysqlipaddress",
						    	   width:300,
						    	   value:"jdbc:mysql://10.128.7.138"
						       },{
						    	   xtype:"label",
						    	   text:"MySQL Port"
						       },
						       {
						    	   xtype:"textfield",
						    	   id:"mysqlport",
						    	   width:300,
						    	   value:"3306"
						       },{
						    	   xtype:"label",
						    	   text:"MySQL Path"
						       },
						       {
						    	   xtype:"textfield",
						    	   id:"metricnamePath",
						    	   width:300,
						    	   value:"/metricname"
						       },{
						    	   xtype:"label",
						    	   text:"Metric Data IP Address"
						       },
						       {
						    	   xtype:"textfield",
						    	   id:"druidipaddress",
						    	   width:300,
						    	   //value:"jdbc:ONEAPM://10.128.7.136"
						    	   value:"jdbc:mysql://10.128.7.138"
						       },{
						    	   xtype:"label",
						    	   text:"Metric Data Port"
						       },
						       {
						    	   xtype:"textfield",
						    	   id:"druidport",
						    	   width:300,
						    	   //value:"8082"
						    	   value:"3306"
						       },{
						    	   xtype:"label",
						    	   text:"Metric Data Path"
						       },
						       {
						    	   xtype:"textfield",
						    	   id:"metricdataPath",
						    	   width:300,
						    	   //value:"/druid/v2"
						    	   value:"/metricdata_pt1m"
						       },
                               {
                                   xtype:"label",
                                   text:"With Pasted Time Data(mins)"
                               },
                               {
                                   xtype:"textfield",
                                   id:"withpasttimedata",
                                   width:300,
                                   value:"180"
                               },
 						       {
                                    xtype:"label",
                                    text:"Application Id"
                               },
                               {
                                    xtype:"textfield",
                                    id:"applicationid",
                                    width:300,
                                    value:"10"
                               },
						       {
						    	   xtype:"button",
						    	   id:"apply",
						    	   text:'Apply',
						    	   listeners:{
						    		   click:{
						    			   fn:function()
						    			   {
						    				   Ext.Ajax.request({
													url : "/correlationresult", //Use real data
						    					    //url : "/testjson.txt", //Use test data
													method : 'GET',
													timeout:13000000,
													params:{
													    chooseTopN:Ext.getCmp('chooseTopN').value,
														chooseMetricName:Ext.getCmp('chooseMetricName').value,
														mysqlipaddress:Ext.getCmp('mysqlipaddress').value,
														mysqlport:Ext.getCmp('mysqlport').value,
														metricnamePath:Ext.getCmp('metricnamePath').value,
														druidipaddress:Ext.getCmp('druidipaddress').value,
														druidport:Ext.getCmp('druidport').value,
														metricdataPath:Ext.getCmp('metricdataPath').value,
														withpasttimedata:Ext.getCmp('withpasttimedata').value,
														applicationid:Ext.getCmp('applicationid').value
													},
													async : true,
													success : function(response, options) {

														var json = JSON.parse(response.responseText);
														var correlationresult = Ext.getCmp('correlationresult_chart');
														correlationresult.rawData = json;
														var data = [];
														for(var i=0;i<json.length;i++)
														{
															var record={baselineId:json[i]['baselineId'],baselineName:json[i]['baselineName']};
															data.push(record);
														}
														correlationresult.getStore().loadData(data,false);
													}
												});
						    			   }
						    		   }
						    	   }
						       }]
					},
					{
						region : "center",
						width : "80%",
						layout:"border",
						xtype: "panel",
						items:[{
							xtype:'correlationresult_panel',
							region:'north',
							height: '100%'
						}]
					}]
		});
	}
});