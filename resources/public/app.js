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
                                    value:"11"
                               },
                               {
                                    xtype:"label",
                                    text:"CRITICAL_VALUE"
                               },
                               {
                                    xtype:"textfield",
                                    id:"grangerCRITICALVALUE",
                                    width:300,
                                    value:"0.85"
                               },
                               {
                                    xtype:"label",
                                    text:"Lag Size"
                               },
                               {
                                    xtype:"textfield",
                                    id:"aLagSize",
                                    width:300,
                                    value:"2"
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
														applicationid:Ext.getCmp('applicationid').value,
														grangerCRITICALVALUE: Ext.getCmp('grangerCRITICALVALUE').value,
														aLagSize: Ext.getCmp('aLagSize').value
													},
													async : true,
													success : function(response, options) {
														var json = eval('('+response.responseText+')');

   													    var panel = Ext.getCmp('baselinetabpanel');
   													    panel.removeAll();
														for(var i=0;i<6;i++){
   													        var baseline = json['baselineNum'][i];
    														var data = [];
    													    for(var j=0;j<baseline.length;j++){
    														        var record = { time: j, num: baseline[j]};
    													        data.push(record);
    													    }
    													    console.log(data);
    													    console.log(Ext.getCmp('baselinetabpanel').getComponent(i));
    													    var store =  Ext.create('Ext.data.JsonStore', {
                                                                                        model : 'TimeSeriesValue',
                                                                                        data : [],
                                                                                        autoLoad : true
                                                                                   });
                                                            store.loadData(data,false);
                                                            var chart = Ext.create('Ext.correlationtest.TimeseriesChart',{
                                                                title: 'Num'+(i+1),
                                                                store: store
                                                            });
                                                            panel.add(chart);
                                                            console.log(i);
    													}
														var jsonresult = json['result'];
                                                        console.log(jsonresult);

                                                        var data = [];
														for(var i=0;i<jsonresult.length;i++){
														    var record = { metricid: jsonresult[i]['metric_id'], metricname: jsonresult[i]['metric_name'],
														                   cscore1: jsonresult[i]['num_result'][0]['coefficient'],
														                   granger1: jsonresult[i]['num_result'][0]['granger'],
														                   cscore2: jsonresult[i]['num_result'][1]['coefficient'],
														                   granger2: jsonresult[i]['num_result'][1]['granger'],
														                   cscore3: jsonresult[i]['num_result'][2]['coefficient'],
														                   granger3: jsonresult[i]['num_result'][2]['granger'],
														                   cscore4: jsonresult[i]['num_result'][3]['coefficient'],
														                   granger4: jsonresult[i]['num_result'][3]['granger'],
														                   cscore5: jsonresult[i]['num_result'][4]['coefficient'],
														                   granger5: jsonresult[i]['num_result'][4]['granger'],
														                   cscore6: jsonresult[i]['num_result'][5]['coefficient'],
														                   granger6: jsonresult[i]['num_result'][5]['granger'],
														                   nums: jsonresult[i]['num_result']};
														    data.push(record);
														}
    												    Ext.getCmp('metrics_grid').getStore().loadData(data,false);

													},
													failure : function(response, options){
													    alert('failure');
													}

												});
						    			   }
						    		   }
						    	   }
						        }
						        ,{
                                    xtype : 'button',
                                    text : 'test',
                                    listeners :{
                                            click:{
                                                fn:function(){
                                                    alert();

                                                }
                                            }
                                    }
						        }
						       ]
					},
					{
/*						region : "center",
						width : "80%",
						layout:"border",
						xtype: "panel",
						items:[{
							xtype:'correlationresult_panel',
							region:'north',
							height: '100%'
						}]*/
						region : "center",
//						width : "60%",
//						height: '100%',
						xtype:'correlationresult_panel',
					}]
		});
	}
});