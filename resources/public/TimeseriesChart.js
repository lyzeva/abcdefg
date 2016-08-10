
Ext.define('Ext.correlationtest.TimeseriesChart',{
    extend : 'Ext.chart.Chart',
    xtype : 'timeserieschart',
    width : 300,
    height : 300,
    store : Ext.create('Ext.data.JsonStore', {
                                                                                        model : 'TimeSeriesValue',
                                                                                        data : [],
                                                                                        autoLoad : true
                                                                                   }),
    axes: [
                                        {
                                            title: 'Time',
                                            type: 'Numeric',
                                            position: 'bottom'
                                        },
                                        {
                                            title: 'Value',
                                            type: 'Numeric',
                                            position: 'left'
                                        }
                                    ],
    series: [
                                        {
                                            type: 'line',
                                            xField: 'time',
                                            yField: 'num'
                                        }
                                    ]

});
