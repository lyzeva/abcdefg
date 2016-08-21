

Ext.define('Ext.correlationtest.Correlationresult', {
			extend : 'Ext.panel.Panel',
			xtype : 'correlationresult_panel',
			autoScroll : true,
			id : 'correlationresult_panel',
			title : 'Correlation Result',
			layout : 'border',
			items : [
			        {
   						xtype : 'timeseriestabpanel',
    					id : 'baselinetabpanel',
    					region : 'north',
						height : '35%',
 						title : 'Chosen Metric Plot',
					},
                    {
						xtype : 'timeseriestabpanel',
						id : 'othermetrictabpanel',
						region : 'center',
 						title : 'Other Metric Plot',
						height : '35%',
					},
					{
						region : 'south',
						height : '30%',
						autoScroll : false,

						xtype : 'grid',
						title:'All the Metrics',
						id : 'metrics_grid',
						layout : 'border',

        						    store : Ext.create('Ext.data.JsonStore', {
        											fields : ['metricid','metricname',
        											        'cscore1','granger1',
        											        'cscore2','granger2',
        											        'cscore3','granger3',
        											        'cscore4','granger4',
        											        'cscore5','granger5',
        											        'cscore6','granger6',
        											        'nums'],
        											data : [],
        											autoLoad : true
        										}),
        							activeItem: 2,
        						    columns : [ {
        											text : 'Metric ID',
        											flex : 48,
        											dataIndex : 'metricid'
        										}, {
        											text : 'Metric Name',
        											flex : 48,
        											dataIndex : 'metricname'
        										}
        										,{
        											text : 'Correlation Score 1',
        											flex : 48,
        											dataIndex : 'cscore1'
        										}
        										,{
        											text : 'Granger Score 1',
        											flex : 48,
        											dataIndex : 'granger1'
        										}
                                                ,{
        											text : 'Correlation Score 2',
        											flex : 48,
        											dataIndex : 'cscore2'
        										},
        										{
        											text : 'Granger Score 2',
        											flex : 48,
        											dataIndex : 'granger2'
        										},
                                               {
        											text : 'Correlation Score 3',
        											flex : 48,
        											dataIndex : 'cscore3'
        										},
        										{
        											text : 'Granger Score 3',
        											flex : 48,
        											dataIndex : 'granger3'
        										},
                                               {
        											text : 'Correlation Score 4',
        											flex : 48,
        											dataIndex : 'cscore4'
        										},
        										{
        											text : 'Granger Score 4',
        											flex : 48,
        											dataIndex : 'granger4'
        										},
                                               {
        											text : 'Correlation Score 5',
        											flex : 48,
        											dataIndex : 'cscore5'
        										},
        										{
        											text : 'Granger Score 5',
        											flex : 48,
        											dataIndex : 'granger5'
        										},
                                               {
        											text : 'Correlation Score 6',
        											flex : 48,
        											dataIndex : 'cscore6'
        										},
        										{
        											text : 'Granger Score 6',
        											flex : 48,
        											dataIndex : 'granger6'
        										}

        							]
                                        ,listeners : {
                                            select :    function(row, record, index, eOpts){
       													    var panel = Ext.getCmp('othermetrictabpanel');
       													    panel.removeAll();
       														var nums = record.raw.nums;

                                                            for(var i=0;i<6;i++){
        														var data = [];
        														var baseline = nums[i]['timeserie'];
        														console.log(baseline);
        													    for(var j=0;j<baseline.length;j++){
        													        var record = { time: j, num: baseline[j]};
        													        data.push(record);
        													    }
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

                                                            }
                                                        }
                                        }


					}]
		});


/*
									style : 'background: #fff',
                        			insetPadding : 20,
                        			animate : false,

 										store : Ext.create(
 												'Ext.data.JsonStore', {
 													fields : ['to', 'value'],

 													proxy : {
 														type : 'ajax',
 														async : true
 													},
 													autoLoad : false
 												}),

                       			    reload : function(applicationName, from, to) {
                        				var url = '/applicationData?from=' + from + '&to=' + to
                        						+ '&applicationName=' + applicationName;

                        				this.axes.items[1].minimum = from;
                        				this.axes.items[1].maximum = to;
                        				this.store.proxy.url = url;
                        				this.store.proxy.type='ajax';
                        				var datefrom = new Date(from);
                        				var dateto = new Date(to);

                        				this.store.reload();
                        				this.redraw(true);

                        			},
                        			shadow : false,
                        			items : [{
                        						type : 'text',
                        						text : '',
                        						font : '16px Helvetica',
                        						width : 70,
                        						height : 20,
                        						x : 40,
                        						y : 12
                        					}
                        					],
                        			axes : [{
                        						type : 'Numeric',
                        						position : 'left',
                        						fields : ['value'],
                        						grid : true
                        					}, {
                        						type : 'Numeric',
                        						position : 'bottom',
                        						fields : ['to'],
                        						grid : true,
                        						label : {
                        							renderer : function(v) {
                        								var date = new Date(v);
                        								return date.getHours() + ':'
                        										+ date.getMinutes() + ':'
                        										+ date.getSeconds();
                        							}
                        						}

                        					}],
                        			series : [{
                        						type : 'line',
                        						axis : 'left',
                        						xField : 'to',
                        						yField : 'value',
                        						markerConfig : {
                        							type : 'circle',
                        							fill : 'green',
                        							radius : 4,
                        							'stroke-width' : 0
                        						},
                        						renderer : function(sprite, record, attr, index, store) {
                        							if (record) {
                        								if (record.raw.anomaly) {
                        									var date = new Date(record.raw.to);
                        									return Ext.apply(attr, {
                        												fill : 'red'
                        											});
                        								} else {
                        									return Ext.apply(attr, {
                        												fill : 'green'
                        											});
                        								}
                        							}
                        							return attr;

                        						}

                        					}]
*/