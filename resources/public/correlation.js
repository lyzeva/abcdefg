

Ext.define('Ext.correlationtest.Correlationresult', {
			extend : 'Ext.panel.Panel',
			xtype : 'correlationresult_panel',
			autoScroll : true,
			id : 'correlationresult_panel',
			title : 'Correlation Result',
			rawData : [];
			layout : 'border',
			items : [{
						xtype : 'tabpanel',
						layout : 'border',
						region : 'north',
 						title : 'Chosen Metric Plot',
						height : '35%',
						items : [{
						    xtype : 'timeserieschart',
						    id : 'cchart1',
						    title : 'Num1'
						}
						,{
						    xtype : 'timeserieschart',
						    id : 'cchart2',
						    title : 'Num2'
						}
						,{
						    xtype : 'timeserieschart',
						    id : 'cchart3',
						    title : 'Num3'
						}
						,{
						    xtype : 'timeserieschart',
						    id : 'cchart4',
						    title : 'Num4'
						}
						,{
						    xtype : 'timeserieschart',
						    id : 'cchart5',
						    title : 'Num5'
						}
						,{
						    xtype : 'timeserieschart',
						    id : 'cchart6',
						    title : 'Num6'
						}
								]
					},
                    {
						xtype : 'tabpanel',
						layout : 'border',
						region : 'center',
 						title : 'Other Metric Plot',
						height : '35%',
						items : [{
						    xtype : 'timeserieschart',
						    id : 'tchart1',
						    title : 'Num1'
						}
						,{
						    xtype : 'timeserieschart',
						    id : 'tchart2',
						    title : 'Num2'
						}
						,{
						    xtype : 'timeserieschart',
						    id : 'tchart3',
						    title : 'Num3'
						}
						,{
						    xtype : 'timeserieschart',
						    id : 'tchart4',
						    title : 'Num4'
						}
						,{
						    xtype : 'timeserieschart',
						    id : 'tchart5',
						    title : 'Num5'
						}
						,{
						    xtype : 'timeserieschart',
						    id : 'tchart6',
						    title : 'Num6'
						}
						]
					},
					{
						xtype : 'panel',
						region : 'south',
						height : '30%',
						autoScroll : false,
						layout : 'border',
						items : [
						{
							xtype : 'grid',
							region : 'west',
							width : '50%',
							height : '100%',
							autoScroll : true,
							title : 'Related Metrics',
								id : 'canaryresult_grid',
								store : Ext.create('Ext.data.JsonStore', {
											fields : ['categoryName', 'score',
													'weight', 'okstates',
													'coldstates',
													'nodatastates', 'hotstates'],
											data : [],
											autoLoad : false

										}),
								columns : [{
											text : 'Category',
											flex : 48,
											dataIndex : 'categoryName'
										}, {
											text : 'Score',
											flex : 18,
											dataIndex : 'score'
										}, {
											text : 'Weight',
											flex : 18,
											dataIndex : 'weight'
										}, {
											text : 'OK States',
											flex : 23,
											dataIndex : 'okstates'
										}, {
											text : 'COLD States',
											flex : 23,
											dataIndex : 'coldstates'
										}, {
											text : 'NODATA States',
											flex : 35,
											dataIndex : 'nodatastates'
										}, {
											text : 'HOT States',
											flex : 23,
											dataIndex : 'hotstates'
										}]
						},
						{
						    title:'All the Metrics',
						    xtype : 'grid',
						    id : 'hotstatescount_grid',
						    region : 'east',
							width : '50%',
							height : '100%',
						    autoScroll:true,
						    store : Ext.create('Ext.data.JsonStore', {
											fields : ['dimenid','metricname',
												    'score1','score2','score3','score4','score5','score6'],
											data : [],
											autoLoad : false

										}),
						    columns : [{
											text : 'Metric ID',
											flex : 48,
											dataIndex : 'dimenid'
										}, {
											text : 'Metric Name',
											flex : 48,
											dataIndex : 'metricname'
										}, {
											text : 'Correlation Score 1',
											flex : 48,
											dataIndex : 'score1'
										},
                                        {
											text : 'Correlation Score 2',
											flex : 48,
											dataIndex : 'score2'
										},
                                       {
											text : 'Correlation Score 3',
											flex : 48,
											dataIndex : 'score3'
										},
                                       {
											text : 'Correlation Score 4',
											flex : 48,
											dataIndex : 'score4'
										},
                                       {
											text : 'Correlation Score 5',
											flex : 48,
											dataIndex : 'score5'
										},
                                       {
											text : 'Correlation Score 6',
											flex : 48,
											dataIndex : 'score6'
										},
										
							]

						}]

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