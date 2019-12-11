
courseMap = new Map();
// idCourse:course

var schemaedit = {
    tongshi : [],//保存所有通识课
    ruxi: [],//同理
    bixiu: [],
    xuanxiu: [],
    xuanxiu_credit: 0,//默认为0
    renwen_credit: 0,
    sheke_credit: 0
};


var dragFrame = {

    ready: function () {

        $(".draggable").draggable({
            revert: "valid",
            helper: "clone",
            appendTo: "body",
            drag: function (event,ui) {

            }
        });

        $(".droppable")
            .droppable({

                drop: function (event, ui) {
                    var id = Number(ui.draggable.prop('id'));
                    var course = courseMap.get(id);

                    //找到课程加入的是哪个表
                    var type = $(this).prop('id');
                    dragFrame.store_course(type,course);
                    //todo 检查重复
                    $(this).find("tbody").append(dragFrame.course_html(course,type));
                    return false;
                }
            });
    },

    store_course: function(type,course)
    {
        alert(type);
        switch (type) {
            case "ruxi":
                schemaedit.ruxi.push(course);
                break;
            case "tongshi":
                schemaedit.tongshi.push(course);
                break;
            case "bixiu":
                schemaedit.bixiu.push(course);
                break;
            case "xuanxiu":
                schemaedit.xuanxiu.push(course);
                break;
        }
    },

    course_html: function (course,type) {
        //type 表的id

        var chinese_name = course.chineseName;
        var credit = course.credit;
        var code = course.BianHao;
        var year = course.year;
        var department = course.department;
        var english_name = course.englishName;
        var season = dragFrame.set_season(course);
        var id = course.idCourse;

        var del = "<button onclick='del("+id+",\""+ type + "\")'>删除</button>";

        //make html

        var html = "<tr class=\""+course.idCourse+"\">\n" +
            "          <th scope=\"row\">"+code+"</th>\n" +
            "          <td>"+chinese_name+"|"+english_name+"</td>\n" +
            "          <td>"+credit+"</td>\n" +
            "          <td>"+season+"</td>\n" +
            "          <td>"+year+"</td>\n" +
            "          <td>"+department+"</td>\n" +
            "          <td>"+del+"</td>"+
            "       </tr>";

        return html;

    },

    set_season: function (course) {
        var season_str = '';
        if(course.spring == 1)
        {
            season_str += "春季学期 "
        }
        else if(course.autume == 1)
        {
            season_str += "秋季学期 "
        }
        else if(course.summer == 1)
        {
            season_str += "夏季学期 "
        }

        return season_str;
    }
};
var loadcourse = {

    departmentMap: new Map(),

    load: function()
    {

        $.ajax({
            type: 'POST',
            // data: data,//json
            contentType: 'application/json',
            dataType: 'json',
            async: false,
            url: '/allcourse',
            success: function (reply) {

                console.log(JSON.stringify(reply));

                for(var i = 0;i < reply.length;i++)
                {
                    courseMap.set(reply[i].idCourse,reply[i]);

                    loadcourse.appendDepartment(reply[i]);
                    loadcourse.appendCourse(reply[i]);
                }
            },
            error: function (response) {
                alert("Error")
            }
        });
    },

    appendCourse: function (course) {
        var name = course.chineseName;
        var courseid = course.idCourse;
        var departmentID = loadcourse.departmentMap.get(course.department);
        var major_html = "<a href=\"#\" class=\"list-group-item list-group-item-action draggable\" id = \""+courseid+"\">"+name+"</a>"

        $('div#'+departmentID).append(major_html);
    },


    appendDepartment: function (course) {

        var department = course.department;

        if(!loadcourse.departmentMap.has(department))
        {
            loadcourse.departmentMap.set(department,loadcourse.departmentMap.size);

            departmentID = loadcourse.departmentMap.get(department);

            var div = "<div class=\"list-group col-md-12\" id = \""+departmentID+"\"></div>";

            var dep_html = "<a href=\"#\" class=\"list-group-item list-group-item-action active\">" +department + "</a>";

            $('#Course_List').append(div);
            $('div#'+departmentID).append(dep_html);
            //append
            console.log("append department:"+department);
        }


    }
};

function upload()
{
    // 按钮按下，上传更新后的培养方案
    console.log(JSON.stringify(schemaedit));
    
}

function transmitList()
{
    $.ajax(
        {
            type: 'POST',
            data: data,//json
            contentType: 'application/json',
            dataType: 'json',
            // async: false,
            url: '/allcourse',
            
            success: function () {
                
            }
        }
    )
}

function transmitInfo()
{
    $.ajax(
        {
            type: 'POST',
            data: data,//json
            contentType: 'application/json',
            dataType: 'json',
            // async: false,
            url: '/allcourse',
        }
    )
}

function deleteCourse(type,course) {
    switch (type) {
        case "ruxi":
            alert('index: '+schemaedit.ruxi.indexOf(course));
            schemaedit.ruxi.splice(schemaedit.ruxi.indexOf(course),1);
            break;
        case "tongshi":
            schemaedit.tongshi.splice(schemaedit.tongshi.indexOf(course),1);
            break;
        case "bixiu":
            schemaedit.bixiu.splice(schemaedit.bixiu.indexOf(course),1);
            break;
        case "xuanxiu":
            schemaedit.xuanxiu.splice(schemaedit.xuanxiu.indexOf(course),1);
            break;
    }
}

function del(id,type)
{
    alert(type.toString());
    $('.'+id).remove();

    var course = courseMap.get(id);
    alert(JSON.stringify(course));
    deleteCourse(type.toString(),course);

}


$(document).ready(loadcourse.load);
$(document).ready(dragFrame.ready);