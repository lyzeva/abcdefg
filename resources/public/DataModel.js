Ext.define('TimeSeriesValue', {
    extend : 'Ext.data.Model',
    fields : [
        {name: 'time', type: 'int'},
        {name: 'num', type:'number'}
    ]
});

Ext.define('IndependentNum',{
    extend : 'Ext.data.Model',
    fields: [
        {name: 'correlationscore', type:'number', defaultValue: Number.NaN}
    ],
    hasMany : {
        model : 'TimeSeriesValue',
        name : 'timeseries'
    }
});

Ext.define('TestMetric',{
    extend : 'Ext.data.Model',
    fields: [
        {name: 'metricid', type:'int'},
        {name: 'metricname', type:'string'}
    ],
    hasMany : {
        model : 'IndependentNum',
        name : 'independentnum'
    }
})