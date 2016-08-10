Ext.define('Ext.correlationtest.TimeseriesTabpanel',{
    extend : 'Ext.tab.Panel',
    xtype : 'timeseriestabpanel',
    width : '100%',
    height : '100%',

						items : [{
        						    xtype : 'timeserieschart',
        						    title : 'Num1'
        						}
        						,{
        						    xtype : 'timeserieschart',
        						    title : 'Num2'
        						}
        						,{
        						    xtype : 'timeserieschart',
        						    title : 'Num3'
        						}
        						,{
        						    xtype : 'timeserieschart',
        						    title : 'Num4'
        						}
        						,{
        						    xtype : 'timeserieschart',
        						    title : 'Num5'
        						}
        						,{
        						    xtype : 'timeserieschart',
        						    title : 'Num6'
        						}
						]
});