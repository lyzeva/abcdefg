Ext.define('Ext.correlationtest.Correlationresult', {
			extend : 'Ext.panel.Panel',
			xtype : 'correlationresult_panel',
			autoScroll : false,
			id : 'correlationresult_panel',
			title : 'Correlation Result',
			layout : 'border',
			items : [{
						xtype : 'panel',
						layout : 'border',
						region : 'west',
						width : '35%',
						items : [{
									xtype : 'panel',
									region : 'north',
									height : '15%',
									items : [{
												xtype : 'text',
												id : 'chart_text',
												text : 'Canary Score',
												style : {
													'font-size' : '30px'
												}
											}]
								},
								{
									xtype : 'chart',
									height : '85%',
									region : 'center',
									id : 'canaryresult_chart',
									height : 310,
									width : 300,
									padding : '10 0 0 0',
									style : 'background: #fff',
									animate : true,
									shadow : false,
									store : Ext.create('Ext.data.JsonStore', {
												fields : ['content', 'percent'],
												data : [],
												autoLoad : true

											}),
									insetPadding : 40,
									series : [{
												type : 'pie',

												colorSet : ['green', 'red'],
												donut : 40,
												angleField : 'percent',
												label : {
													field : 'content',
													display : 'inside',
													calloutLine : true
												},
												highlight : {
													segment : {
														margin : 20
													}
												}
											}]
								}]
					},
					{
						xtype : 'tabpanel',
						region : 'center',
						width : '65%',
						autoScroll : false,
						layout : 'border',
						items : [{
							xtype : 'panel',
							layout : 'border',
							title : 'Overview',
							autoScroll : false,
							items : [{
								xtype : 'grid',
								region : 'north',
								height : '50%',
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
								xtype : 'grid',
								region : 'center',
								height : '50%',
								id : 'canarycount_grid',
								store : Ext.create('Ext.data.JsonStore', {
											fields : ['bettermetrics',
													'worsemetrics',
													'unchangedmetrics'],
											data : [],
											autoLoad : false

										}),
								columns : [{
											text : 'Better Metrics',
											flex : 48,
											dataIndex : 'bettermetrics'
										}, {
											text : 'Worse Metrics',
											flex : 48,
											dataIndex : 'worsemetrics'
										}, {
											text : 'Unchanged Metrics',
											flex : 48,
											dataIndex : 'unchangedmetrics'
										}]
							}]
						},
						{
						    title:'HOT States',
						    xtype : 'grid',
						    id : 'hotstatescount_grid',
						    autoScroll:true,
						    store : Ext.create('Ext.data.JsonStore', {
											fields : ['categoryname',
													'dimenid','metricname',
													'metrictypename','ratio'],
											data : [],
											autoLoad : false

										}),
						    columns : [{
											text : 'Dimension ID',
											flex : 48,
											dataIndex : 'dimenid'
										}, {
											text : 'Metric Name',
											flex : 48,
											dataIndex : 'metricname'
										}, {
											text : 'Metric Type',
											flex : 48,
											dataIndex : 'metrictypename'
										}, {
											text : 'Category Name',
											flex : 48,
											dataIndex : 'categoryname'
										}, {
											text : 'Ratio',
											flex : 48,
											dataIndex : 'ratio'
										}
							]

						}]

					}]
		});