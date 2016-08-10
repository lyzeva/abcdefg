

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
						xtype : 'panel',
						region : 'south',
						height : '30%',
						autoScroll : false,
						layout : 'border',
						items : [

        						{
        						    title:'All the Metrics',
        						    xtype : 'grid',
        						    id : 'metrics_grid',
        						    region : 'east',
        							width : '50%',
        							height : '100%',
        						    autoScroll:true,
        						    store : Ext.create('Ext.data.JsonStore', {
        											model : 'TestMetric',
        											data : [],
        											autoLoad : true
        										}),
        						    columns : [{
        											text : 'Metric ID',
        											flex : 48,
        											dataIndex : 'metricid'
        										}, {
        											text : 'Metric Name',
        											flex : 48,
        											dataIndex : 'metricname'
        										}, {
        											text : 'Correlation Score 1',
        											flex : 48,
        											dataIndex : 'independentnum.correlationscore'
        										},
                                                {
        											text : 'Correlation Score 2',
        											flex : 48,
        											dataIndex : 'independentnum.correlationscore'
        										},
                                               {
        											text : 'Correlation Score 3',
        											flex : 48,
        											dataIndex : 'independentnum.correlationscore'
        										},
                                               {
        											text : 'Correlation Score 4',
        											flex : 48,
        											dataIndex : 'independentnum.correlationscore'
        										},
                                               {
        											text : 'Correlation Score 5',
        											flex : 48,
        											dataIndex : 'independentnum.correlationscore'
        										},
                                               {
        											text : 'Correlation Score 6',
        											flex : 48,
        											dataIndex : 'independentnum.correlationscore'
        										},

        							]
 /*       							listeners : {
        							    select :    function(this, record, index, eOpts){
        							                    alert('a record selected');
        							                    var c = this.getStore().independentnum;


        							                }
        							}
*/
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