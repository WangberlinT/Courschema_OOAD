package com.exercise.springproject.web;

import com.exercise.springproject.domain.*;
import com.exercise.springproject.service.*;
import jxl.Workbook;
import jxl.write.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.*;

@Controller
@RestController
public class CourschemasController {
    @Autowired
    private CourschemasService courschemasService;

    @Autowired
    private DepartmentService departmentService;

    @Autowired
    private ClassificationService classificationService;

    @Autowired
    private CourseService courseService;

    @Autowired
    private MajorService majorService;

    @Autowired
    private XianxiuService xianxiuService;



    @GetMapping("/recordCourschemas")
    public List<courschemas> findAllCourschemas(){
        return courschemasService.findAll();
    }

    @GetMapping(value = "allschema")
    @ResponseBody
    public List<Map>  allschema(){
         List<courschemas>  search = courschemasService.findAll();
         List<Map> reply = new LinkedList<>();
         for(courschemas now: search) {
             Map<String, Object> tmp = new HashMap<String, Object>();
             tmp.put("name", now.getChineseName());//name
             tmp.put("year", now.getNian());//year
             tmp.put("dept", now.getDepartment_name());
             tmp.put("major", now.getMajor_name());
             String foreign = now.getWaiGuo() == 0?"否":"是";//foreign
             tmp.put("foreign", foreign);
             String one_plus3 = now.getOne_plus3() == 0?"2+2培养":"1+3培养";
             tmp.put("type", one_plus3);//type
             tmp.put("intro", now.getIntro());
             tmp.put("id", now.getCourschema());

             //todo put intro

//             tmp.put("major_elec_alt", now.getMajor_elec_alt());
////             tmp.put("altered_course1", now.getAltered_course1());
////             tmp.put("altered_course2", now.getAltered_course2());
//             tmp.put("coursechema", now.getCourschema());
//
//
//
//
//             tmp.put("major_elec", now.getMajor_elec());
//             tmp.put("HU_elec", now.getHU_elec());
//             tmp.put("SS_elec", now.getSS_elec());
//             tmp.put("AR_elec", now.getAR_elec());
//             tmp.put("political", now.getPolitical());

             reply.add(tmp);
         }
        return reply;
    }


    @PostMapping(value = "schemabyMajor")
    @ResponseBody
    public List<courschemas> schemabyMajor(@RequestBody Integer majorId){
        return courschemasService.findcourschemasBymajor(majorId);
    }

    @PostMapping(value = "schemabyMajorAndYear")
    @ResponseBody
    public List<courschemas> schemabyMajorAndYear(@RequestBody Integer majorId,
                                                  @RequestBody Integer year){
        List<courschemas> first = courschemasService.findcourschemasBymajor(majorId);
        List<courschemas> ans = null;
        for(courschemas now:first){
            if(now.getNian()==year){
                ans.add(now);
            }
        }
        return ans;
    }


    @PostMapping("/allCourschemas")
    @ResponseBody
    public List<Object> findCourschemas(){
        List<Object> result = new ArrayList<Object>();
        List<courschemas> all = courschemasService.findAll();
        for(int i = 0;i < all.size();i ++) {
            Map<String,Object> ele = new HashMap<>();
            courschemas c = all.get(i);
            ele.put("chineseName",c.getChineseName());
            int dep_id = c.getDepartment();
            System.out.println(dep_id);
            Department d = departmentService.findDepartmentById(dep_id);
            ele.put("department",d.getChineseName());
            ele.put("courschema",c.getCourschema());
            ele.put("AR_elec", c.getAR_elec());
            ele.put("HU_elec", c.getHU_elec());
            ele.put("SS_elec", c.getSS_elec());
            ele.put("major_elec", c.getMajor_elec());
            ele.put("foreign", c.getWaiGuo());
            ele.put("one_plus3", c.getOne_plus3());
            ele.put("intro", c.getIntro());
            ele.put("year", c.getNian());
            Major m = majorService.findMajorById(c.getMajor());
            ele.put("major", m.getChineseName());
            result.add(ele);
        }

        return result;
    }

    @PostMapping("/oneCourschemas")
    @ResponseBody
    public Map<String, Object> findOneCourschemas(@RequestBody int id){
        courschemas c = courschemasService.findcourschemasById(id);
        Map<String,Object> ele = new HashMap<>();
        ele.put("chineseName",c.getChineseName());
        int dep_id = c.getDepartment();
        System.out.println(dep_id);
        Department d = departmentService.findDepartmentById(dep_id);
        ele.put("department",d.getChineseName());
        ele.put("courschema",c.getCourschema());
        ele.put("AR_elec", c.getAR_elec());
        ele.put("HU_elec", c.getHU_elec());
        ele.put("SS_elec", c.getSS_elec());
        ele.put("major_elec", c.getMajor_elec());
        ele.put("foreign", c.getWaiGuo());
        ele.put("one_plus3", c.getOne_plus3());
        ele.put("intro", c.getIntro());
        ele.put("year", c.getNian());
        Major m = majorService.findMajorById(c.getMajor());
        ele.put("major", m.getChineseName());
        return ele;
    }


//    @PostMapping("/recordCourschemas")
//    public courschemas addOne(courschemas courschemas){
//        System.out.print(courschemas);
//        return courschemasService.save(courschemas);
//    }

//    @PutMapping("/recordCourschemas")
//    public courschemas update(//@RequestParam int courschema,
//                              @RequestParam int Foreign,
//                              @RequestParam int one_plus3,
//                              @RequestParam int major_elec_art,
////                              @RequestParam int altered_course1,
////                              @RequestParam int altered_course2,
//                              @RequestParam int Major,
//                              @RequestParam int Year,
//                              @RequestParam int Department,
//                              @RequestParam int major_elec,
//                              @RequestParam int HU_elec,
//                              @RequestParam int SS_elec,
//                              @RequestParam int AR_elec,
//                              @RequestParam int political,
//                              @RequestParam String ChineseName){
//        courschemas courschemas = new courschemas();
//     //   courschemas.setCourschema(courschema);
////        courschemas.setAltered_course1(altered_course1);
////        courschemas.setAltered_course2(altered_course2);
//        courschemas.setAR_elec(AR_elec);
//        courschemas.setChineseName(ChineseName);
//        courschemas.setDepartment(Department);
//        courschemas.setWaiGuo(Foreign);
//        courschemas.setHU_elec(HU_elec);
//        courschemas.setMajor(Major);
//        courschemas.setMajor_elec(major_elec);
//        courschemas.setMajor_elec_alt(major_elec_art);
//        courschemas.setOne_plus3(one_plus3);
//        courschemas.setPolitical(political);
//        courschemas.setSS_elec(SS_elec);
//        courschemas.setNian(Year);
//        return courschemasService.save(courschemas);
//    }

//    @PostMapping("/saveCourschemas")
//    public courschemas addOne(courschemas courschemas){
//        return courschemasService.save(courschemas);
//    }


    @PostMapping(value="/editCou")
    @ResponseBody
    public Map editSchema(@RequestBody Map<String, Object> map) {
        int schemaid = (int) map.get("id");
        int AR_elec = ((int)map.get("AR_elec"));
        String ChineseName = ((String) map.get("schema_name"));
        String depart = (String) map.get("department");
        Department de = departmentService.findDepartmentByName(depart);
        int departid = de.getIdDepartment();
        Map<String, Object> reply = new HashMap<>();
        if(de==null){
            reply.put("state","fail");
            return reply;
        }
        int WaiGuo = ((int) map.get("foreign"));
        int HU_elec = ((int)map.get("HU_elec"));
        String major = (String) map.get("major");
        Major m = majorService.findMajorByCname(major);
        int mid = m.getIdMajor();
        if(m==null){
            reply.put("state","fail");
            return reply;
        }
        int Major_elec =  ((int)map.get("major_elec"));
        int One_plus3 = ((int)map.get("one_plus3"));
        int SS_elec = ((int)map.get("SS_elec"));
        int Nian = ((int)map.get("year"));
        String Intro = ((String)map.get("intro"));
        courschemasService.editSchema(schemaid, AR_elec, ChineseName, depart, departid, WaiGuo, HU_elec, major, mid, Major_elec,
                One_plus3, SS_elec, Nian, Intro);

        reply.put("state", "success");
        return reply;
    }

    @PostMapping(value="/saveCou")
    @ResponseBody
    public Map saveSchema(@RequestBody Map<String, Object> map){
        courschemas c = new courschemas();
        System.out.println(c);
        System.out.println(c.getCourschema());
        System.out.println(map.get("department"));
        System.out.println(map.get("AR_elec"));
        System.out.println(map.get("schema_name"));
        System.out.println(map.get("major_elec"));
        System.out.println(map.get("HU_elec"));
        System.out.println(map.get("foreign"));
        System.out.println(map.get("major"));
        System.out.println(map.get("one_plus3"));

        c.setAR_elec((int)map.get("AR_elec"));
        c.setChineseName((String) map.get("schema_name"));
        //String depart = (String) map.get("department");
        String depart = (String) map.get("department");
        Department de = departmentService.findDepartmentByName(depart);
        Map<String, Object> reply = new HashMap<>();
        if(de==null){
            reply.put("state","fail");
            return reply;
        }
        c.setDepartment_name(depart);
        c.setDepartment(de.getIdDepartment());
        c.setWaiGuo((int) map.get("foreign"));
        c.setHU_elec((int)map.get("HU_elec"));
        //String major = (String) map.get("major");
        String major = (String) map.get("major");
        Major m = majorService.findMajorByCname(major);
        if(m==null){
            reply.put("state","fail");
            return reply;
        }
        c.setMajor_name(major);
        c.setMajor(majorService.findMajorByCname(major).getIdMajor());
        c.setMajor_elec((int)map.get("major_elec"));
        c.setOne_plus3((int)map.get("one_plus3"));
        c.setSS_elec((int)map.get("SS_elec"));
        c.setNian((int)map.get("year"));
        c.setIntro((String)map.get("intro"));

        courschemasService.save(c);
        reply.put("state","success");
        return reply;
        //return reply;
    }

    @PostMapping(value="/showCou")
    @ResponseBody
    public Map<String, Object> showSchema(@RequestBody int schema_id){
        courschemas search = findCourschema(schema_id);
        Map<String, Object> reply = new HashMap<>();
        int de = search.getDepartment();
        Department d = departmentService.findDepartmentById(de);
        reply.put("department", d.getChineseName());
        reply.put("AR_elec", search.getAR_elec());
        reply.put("schema_name", search.getChineseName());
        reply.put("major_elec", search.getMajor_elec());
        reply.put("HU_elec", search.getHU_elec());
        reply.put("foreign", search.getWaiGuo());
        Major m = majorService.findMajorById(search.getMajor());
        reply.put("major", m.getChineseName());
        reply.put("one_plus3", search.getOne_plus3());
        reply.put("SS_elec", search.getSS_elec());
        reply.put("year", search.getNian());
        reply.put("intro", search.getIntro());

        return reply;
    }



    @DeleteMapping("recordCourschemas/{courschema}")
    public void deleteCourschema(@PathVariable int courschema){
        courschemasService.deleteCourschema(courschema);
    }

    @PostMapping("findCourschemaId")
    public courschemas findCourschema(@RequestParam int courschema){
        return courschemasService.findCourschema(courschema);
    }

    @PostMapping("findCourschemaName")
    public courschemas findCourschemaName(@RequestParam String chinese_name){
        return courschemasService.findCourschemaName(chinese_name);
    }

    @PostMapping(value="/downloadCourschemas")
    @ResponseBody
    public Map downloadCourschema(@RequestBody int idCourschema) throws IOException {
        System.out.println("download");
        courschemas  courschema= courschemasService.findcourschemasById(idCourschema);
        String path = "D:/" + courschema.getChineseName() + ".xls";
        WritableFont fontTitle = new WritableFont(WritableFont.createFont("宋体"), 12, WritableFont.NO_BOLD);
        WritableCellFormat formatTitle = new WritableCellFormat(fontTitle);
        try{
            formatTitle.setWrap(true);
        } catch (WriteException e) {
            e.printStackTrace();
        }

        File filewrite=new File(path);
        filewrite.createNewFile();
        OutputStream os=new FileOutputStream(filewrite);
        writeExcel(os, courschema, idCourschema);
        os.close();
        Map<String,Object> reply = new HashMap<>();
        reply.put("status","success");
        return reply;
    }

    public void writeExcel(OutputStream os, courschemas courschema, int idCourschema)
    {
        try
        {
            WritableWorkbook wwb = Workbook.createWorkbook(os);
            WritableSheet ws = wwb.createSheet("Test Sheet 1",0);
            ws.setColumnView(0, 25);
            ws.setColumnView(1, 30);
            ws.setColumnView(1, 15);
            ws.setColumnView(4, 25);

            int year = 1;
            String semester = "autumn";
            String time = "";
            Course course;


            Label label = new Label(0,0,courschema.getChineseName());
            ws.addCell(label);
            label = new Label(0,1, "院系");
            ws.addCell(label);
            label = new Label(1,1, courschema.getDepartment_name());
            ws.addCell(label);

            label = new Label(0,2, "专业");
            ws.addCell(label);
            label = new Label(1,2, courschema.getMajor_name());
            ws.addCell(label);

            label = new Label(0,3, "introduction");
            ws.addCell(label);
            label = new Label(1,3, courschema.getIntro());
            ws.addCell(label);

            label = new Label(0,4, "type");
            ws.addCell(label);
            if(courschema.getOne_plus3()==1){
                label = new Label(1,4, "1+3");
            }
            else{
                label = new Label(1,4, "2+2");
            }
            ws.addCell(label);

            label = new Label(0,5, "专业选修学分");
            ws.addCell(label);
            label = new Label(1,5, String.valueOf(courschema.getMajor_elec()));
            ws.addCell(label);

            label = new Label(0,6, "人文选修学分");
            ws.addCell(label);
            label = new Label(1,6, String.valueOf(courschema.getHU_elec()));
            ws.addCell(label);

            label = new Label(0,7, "社科选修学分");
            ws.addCell(label);
            label = new Label(1,7, String.valueOf(courschema.getSS_elec()));
            ws.addCell(label);

            label = new Label(0,8, "艺术选修学分");
            ws.addCell(label);
            label = new Label(1,8, String.valueOf(courschema.getAR_elec()));
            ws.addCell(label);

            label = new Label(0,9, "思政学分");
            ws.addCell(label);
            label = new Label(1,9, String.valueOf(courschema.getPolitical()));
            ws.addCell(label);

            label = new Label(0,10, "入学年份");
            ws.addCell(label);
            label = new Label(1,10, String.valueOf(courschema.getNian()));
            ws.addCell(label);

            label = new Label(0,11, "国际生培养方案");
            ws.addCell(label);
            if(courschema.getWaiGuo()==1){
                label = new Label(1,11, "yes");
            }
            else{
                label = new Label(1,111, "no");
            }
            ws.addCell(label);

            label = new Label(0,12, "通识理工基础课");
            ws.addCell(label);
            label = new Label(0,13, "Chinese Name");
            ws.addCell(label);
            label = new Label(1,13, "English Name");
            ws.addCell(label);
            label = new Label(2,13, "Code");
            ws.addCell(label);
            label = new Label(3,13, "Credit");
            ws.addCell(label);
            label = new Label(4,13, "Suggested Time");
            ws.addCell(label);
            label = new Label(5,13, "Pre-request Course");
            ws.addCell(label);

            List<Integer> idCourses = this.classificationService.findTypeTonCourse(idCourschema);
            List<Course> courses = new LinkedList<Course>();
            courses.clear();
            for(int id: idCourses){
                courses.add(courseService.findCourseById(id));
            }
            int len1 = courses.size();
            for(int i=0; i<len1; i++){
                course = courses.get(i);
                label = new Label(0,14+i, course.getChineseName());

                ws.addCell(label);

                label = new Label(1,14+i, course.getEnglishName());
                ws.addCell(label);
                label = new Label(2,14+i, course.getBianHao());
                ws.addCell(label);
                label = new Label(3,14+i, String.valueOf(course.getCredit()));
                ws.addCell(label);
                year = course.getNian();
                if(course.getAutumn()==1){
                    time = "year " + year + " autumn";
                    if(course.getSpring()==1){
                        time += " &spring";
                    }
                    if(course.getSummer()==1){
                        time += " &summer";
                    }
                }
                else if(course.getSpring()==1){
                    time = "year " + year + " spring";
                    if(course.getAutumn()==1){
                        time += " &autumn";
                    }
                    if(course.getSummer()==1){
                        time += " &summer";
                    }
                }
                else if(course.getSummer()==1){
                    time = "year " + year + " summer";
                    if(course.getSpring()==1){
                        time += " &spring";
                    }
                    if(course.getAutumn()==1){
                        time += " &autumn";
                    }
                }
                else{
                    time = "year " + year;
                }
                label = new Label(4,14+i, time);
                ws.addCell(label);

                label = new Label(5, 14+i, course.getXianxiu());
                ws.addCell(label);
            }

            label = new Label(0,15+len1, "专业先修课");
            //System.out.println("1st: "+String.valueOf(4+len1));
            ws.addCell(label);
            label = new Label(0,16+len1, "Chinese Name");
            ws.addCell(label);
            label = new Label(1,16+len1, "English Name");
            ws.addCell(label);
            label = new Label(2,16+len1, "Code");
            ws.addCell(label);
            label = new Label(3,16+len1, "Credit");
            ws.addCell(label);
            label = new Label(4,16+len1, "Suggested Time");
            ws.addCell(label);
            idCourses = this.classificationService.findTypeRuxiCourse(idCourschema);
            courses.clear();
            for(int id: idCourses){
                courses.add(courseService.findCourseById(id));
            }
            int len2 = courses.size();
            //System.out.println("len2: "+len2);
            for(int i=0; i<len2; i++){
                course = courses.get(i);
                label = new Label(0,17+len1+i, course.getChineseName());
                ws.addCell(label);
                //System.out.println(course.getChineseName());

                label = new Label(1,17+len1+i, course.getEnglishName());
                ws.addCell(label);
                label = new Label(2,17+len1+i, course.getBianHao());
                ws.addCell(label);
                label = new Label(3,17+len1+i, String.valueOf(course.getCredit()));
                ws.addCell(label);
                year = course.getNian();
                if(course.getAutumn()==1){
                    time = "year " + year + " autumn";
                    if(course.getSpring()==1){
                        time += " &spring";
                    }
                    if(course.getSummer()==1){
                        time += " &summer";
                    }
                }
                else if(course.getSpring()==1){
                    time = "year " + year + " spring";
                    if(course.getAutumn()==1){
                        time += " &autumn";
                    }
                    if(course.getSummer()==1){
                        time += " &summer";
                    }
                }
                else if(course.getSummer()==1){
                    time = "year " + year + " summer";
                    if(course.getSpring()==1){
                        time += " &spring";
                    }
                    if(course.getAutumn()==1){
                        time += " &autumn";
                    }
                }
                else{
                    time = "year " + year;
                }
                label = new Label(4,17+len1+i, time);
                ws.addCell(label);

                label = new Label(5, 17+len1+i, course.getXianxiu());
                ws.addCell(label);


            }
            label = new Label(0,18+len1+len2, "专业核心课");
            //System.out.println("2st: "+String.valueOf(5+len1+len2));
            ws.addCell(label);
            label = new Label(0,19+len1+len2, "Chinese Name");
            ws.addCell(label);
            label = new Label(1,19+len1+len2, "English Name");
            ws.addCell(label);
            label = new Label(2,19+len1+len2, "Code");
            ws.addCell(label);
            label = new Label(3,19+len1+len2, "Credit");
            ws.addCell(label);
            label = new Label(4,19+len1+len2, "Suggested Time");
            ws.addCell(label);
            idCourses = this.classificationService.findTypeComCourse(idCourschema);
            courses.clear();
            for(int id: idCourses){
                courses.add(courseService.findCourseById(id));
            }
            int len3 = courses.size();
            //System.out.println("len3: "+len3);
            for(int i=0; i<len3; i++){
                course = courses.get(i);
                label = new Label(0,20+len1+len2+i, course.getChineseName());
                ws.addCell(label);
                //System.out.println(course.getChineseName());

                label = new Label(1,20+len1+len2+i, course.getEnglishName());
                ws.addCell(label);
                label = new Label(2,20+len1+len2+i, course.getBianHao());
                ws.addCell(label);
                label = new Label(3,20+len1+len2+i, String.valueOf(course.getCredit()));
                ws.addCell(label);
                year = course.getNian();
                if(course.getAutumn()==1){
                    time = "year " + year + " autumn";
                    if(course.getSpring()==1){
                        time += " &spring";
                    }
                    if(course.getSummer()==1){
                        time += " &summer";
                    }
                }
                else if(course.getSpring()==1){
                    time = "year " + year + " spring";
                    if(course.getAutumn()==1){
                        time += " &autumn";
                    }
                    if(course.getSummer()==1){
                        time += " &summer";
                    }
                }
                else if(course.getSummer()==1){
                    time = "year " + year + " summer";
                    if(course.getSpring()==1){
                        time += " &spring";
                    }
                    if(course.getAutumn()==1){
                        time += " &autumn";
                    }
                }
                else{
                    time = "year " + year;
                }
                label = new Label(4,20+len1+len2+i, time);
                ws.addCell(label);
                label = new Label(5, 20+len1+len2+i, course.getXianxiu());
                ws.addCell(label);
            }
            //System.out.println("3st: "+String.valueOf(5+len1+len2+len3));

            label = new Label(0,21+len1+len2+len3, "思政课");
            //System.out.println("2st: "+String.valueOf(5+len1+len2));
            ws.addCell(label);
            label = new Label(0,22+len1+len2+len3, "Chinese Name");
            ws.addCell(label);
            label = new Label(1,22+len1+len2+len3, "English Name");
            ws.addCell(label);
            label = new Label(2,22+len1+len2+len3, "Code");
            ws.addCell(label);
            label = new Label(3,22+len1+len2+len3, "Credit");
            ws.addCell(label);
            label = new Label(4,22+len1+len2+len3, "Suggested Time");
            ws.addCell(label);
            idCourses = this.classificationService.findPoliticalCourse(idCourschema);
            courses.clear();
            for(int id: idCourses){
                courses.add(courseService.findCourseById(id));
            }
            int len4 = courses.size();
            //System.out.println("len3: "+len3);
            for(int i=0; i<len4; i++){
                course = courses.get(i);
                label = new Label(0,23+len1+len2+len3+i, course.getChineseName());
                ws.addCell(label);
                //System.out.println(course.getChineseName());

                label = new Label(1,23+len1+len2+len3+i, course.getEnglishName());
                ws.addCell(label);
                label = new Label(2,23+len1+len2+len3+i, course.getBianHao());
                ws.addCell(label);
                label = new Label(3,23+len1+len2+len3+i, String.valueOf(course.getCredit()));
                ws.addCell(label);
                year = course.getNian();
                if(course.getAutumn()==1){
                    time = "year " + year + " autumn";
                    if(course.getSpring()==1){
                        time += " &spring";
                    }
                    if(course.getSummer()==1){
                        time += " &summer";
                    }
                }
                else if(course.getSpring()==1){
                    time = "year " + year + " spring";
                    if(course.getAutumn()==1){
                        time += " &autumn";
                    }
                    if(course.getSummer()==1){
                        time += " &summer";
                    }
                }
                else if(course.getSummer()==1){
                    time = "year " + year + " summer";
                    if(course.getSpring()==1){
                        time += " &spring";
                    }
                    if(course.getAutumn()==1){
                        time += " &autumn";
                    }
                }
                else{
                    time = "year " + year;
                }
                label = new Label(4,23+len1+len2+len3+i, time);
                ws.addCell(label);
                label = new Label(5, 23+len1+len2+len3+i, course.getXianxiu());
                ws.addCell(label);
            }

            wwb.write();
            wwb.close();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

}
