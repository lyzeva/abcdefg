
Ext.define('Ext.correlationtest.TimeseriesChart',{
    extend : 'Ext.chart.Chart',
    xtype : 'timeserieschart',
    width : 300,
    height : 300,
    store :  {
            model : 'TimeSeriesValue',
            data :  []
    },
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
