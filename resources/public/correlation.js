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
						region : 'north',
						height : '35%',
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
												fields : ['time', 'value'],
												data : [],
												autoLoad : true

											}),
									insetPadding : 40,
									series : [{
												type : 'line',

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
						xtype : 'panel',
						region : 'center',
						height : '65%',
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