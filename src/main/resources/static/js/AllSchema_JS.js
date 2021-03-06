

function getData()
{
    var data = [];

    $.ajax(
        {
            type: 'GET',
            // data: data,//json
            contentType: 'application/json',
            dataType: 'json',
            async: false,
            url: '/allschema',
            success: function (reply) {
                console.log(JSON.stringify(reply));
                //reply[i]:{"major":"计算机科学与技术","year":2020,"name":"测试测试","dept":"计算机科学与工程系","type":"1+3培养","foreign":"是"}
                for(i = 0;i < reply.length;i ++) {
                    var ele = reply[i];

                    var item = {
                        name: ele.name,
                        year: ele.year,
                        dept: ele.dept,
                        major: ele.major,
                        foreign: ele.foreign,
                        type: ele.type,
                        check: '<a href="/courschemaEdit?id='+ele.id+'">编辑</a>'
                    };
                    data.push(item);
                }
            },
            error: function (reply) {
                alert("error")
            }
        }
    );
    return data;

}

var loadtable = {
    columns : [{
        field: 'name',
        title: '培养方案名称'
    }, {
        field: 'year',
        title: '适用学年'
    }, {
        field: 'dept',
        title: '院系'
    },{
        field: 'major',
        title: '专业'
    },{
        field: 'foreign',
        title: 'foreign student'
    },{
        field: 'type',
        title: '培养类型'
    },{
        field: 'check',
        title: '查看详情'
    }
    ],


load: function () {
        $('#schematable').bootstrapTable({
            columns: loadtable.columns,
            data: getData(),
            classes: "table table-bordered table-striped table-sm table-dark",
            //******前端分页设置****
            pagination:true,
            pageNumber:1,
            pageSize:10,
            pageList:"[10, 20, 50, 200]",
            paginationHAlign:"left",
            paginationDetailHAlign:"right"
            //******前端分页设置****
        });

    }
};

$(document).ready(loadtable.load);