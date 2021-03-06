new Vue({
    el: "#courseDesc",
    data: {
        courses: [],
        showTable: [],
        departments: [],
        currentPage: 1,
        loading: true,
        loadingDepartment: true,
        totalPages: 1,
        showNum: 0,
        currentDepart: '计算机科学与工程系',
        currentYear: 1,
        courseNum: 0,

        modalChineseName: "",
        modalBianHao: "",
        modalIntro: "",
        modalCredit: '',
        modalTerm: '',
        modalYear: "",
        modalDepartments: "",
        modalEnglishName: "",
        modalXianxiu: "",

        userId: Number(getUrlParam('param')),

        isUpdate: false,

        updateIdCourse: -1,
        updateChineseName: "",
        updateBianHao: "",
        updateIntro: "",
        updateCredit: 3,
        updateSpring: false,
        updateAutumn: false,
        updateSummer: false,
        updateRequirement: "",
        updateYear: "大一",
        updateDepartment: "",
        updateEnglishName: "",

        deleteCourseId: -1

    },

    methods: {
        select: function (n) {
            if (n === this.currentPage) return
            if (typeof n === 'string') return
            this.currentPage = n
        },

        prevOrNext: function (n) {
            this.currentPage += n
            this.currentPage < 1 ?
                this.currentPage = 1 :
                this.currentPage > this.totalPages ?
                    this.currentPage = this.totalPages :
                    null
        },

        isNull(str) {
            if (str == "") {
                return true;
            }
            var regu = "^[ ]+$";
            var re = new RegExp(regu);
            return re.test(str);
        },

        generateYear(course) {
            if (course == null) {
                console.log("课程不存在")
                return "该课程不存在";
            }

            _year = '';
            switch (course.year) {
                case 1:
                    _year = "大一";
                    break;
                case 2:
                    _year = "大二";
                    break;
                case 3:
                    _year = "大三";
                    break;
                case 4:
                    _year = "大四";
                    break;
                default:
                    _year = " ";
            }
            return _year;
        },

        setCurrentYear(year) {
            this.currentYear = year;
        },

        setCurrentDepart(depart) {
            this.currentDepart = depart;
        },

        // deepCopy = function (obj) {
        //     // 只拷贝对象
        //     if (typeof obj !== 'object') return;
        //     // 根据obj的类型判断是新建一个数组还是一个对象
        //     var newObj = obj instanceof Array ? [] : {};
        //     for (var key in obj) {
        //         // 遍历obj,并且判断是obj的属性才拷贝
        //         if (obj.hasOwnProperty(key)) {
        //             // 判断属性值的类型，如果是对象递归调用深拷贝
        //             newObj[key] = typeof obj[key] === 'object' ? deepCopy(obj[key]) : obj[key];
        //         }
        //     }
        //     return newObj;
        // }

        generateTerm(course) {
            _term = "";
            if (course.spring) {
                _term += "/春季期";
            }
            if (course.autumn) {
                _term += "/秋季期";
            }
            if (course.summer) {
                _term += "/夏季小学期"
            }

            if (_term === "") {
                return "已停止开设";
            } else {
                _term[1] = " ";
                return _term;
            }
        },

        setModalcontent(course) {
            this.modalTerm = this.generateTerm(course);
            this.modalChineseName = course.chineseName;
            this.modalBianHao = course.BianHao;
            this.modalIntro = course.intro;
            this.modalCredit = course.credit;
            this.modalYear = this.generateYear(course);
            this.modalDepartments = course.department;
            this.modalEnglishName = course.englishName;
            this.modalXianxiu = course.xianxiu;
            $('#courseModal').modal('show')
        },

        updateCourse() {
            this.loading = true;
            var url;
            var contentl;
            if (this.isUpdate) {
                url = "/editcourse";
                content = {
                    course_id: this.updateIdCourse,
                    chinese_name: this.updateChineseName,
                    code: this.updateBianHao,
                    intro: this.updateIntro,
                    credit: this.updateCredit,
                    summer: this.updateSummer,
                    spring: this.updateSpring,
                    autumn: this.updateAutumn,
                    xianxiu: this.updateRequirement,
                    english_name: this.updateEnglishName,
                    year: this.updateYear,
                    department: this.updateDepartment,
                    user_id: this.userId
                }
            } else {
                url = "/newcourse";
                content = {
                    chinese_name: this.updateChineseName,
                    code: this.updateBianHao,
                    intro: this.updateIntro,
                    credit: this.updateCredit,
                    summer: this.updateSummer,
                    spring: this.updateSpring,
                    autumn: this.updateAutumn,
                    xianxiu: this.updateRequirement,
                    english_name: this.updateEnglishName,
                    year: this.updateYear,
                    department: this.updateDepartment,
                    user_id: this.userId
                }
            }
            console.log(content)
            axios
                .post(url,
                    content)
                .then(response => {
                    if (response.data.state === "success") {
                        if (this.isUpdate){
                            alert("修改成功");
                            this.courses.push(content);
                        }else {
                            alert("添加成功");
                            var course_id = response.data.course_id;
                            axios
                                .post('/onecourse', {id: course_id})
                                .then(response => {
                                    this.courses.push(response.data)
                                })
                                .catch(error => {
                                    console.log(error);
                                    alert("课程信息接收错误， 请联系相关负责人员");
                                })

                        }
                    } else if (response.data.state === "fail") {
                        alert("添加失败")
                    } else if (response.data.state === "not match"){
                        alert("您没有权限")
                    } else {
                        alert("后端的锅：请找后端相关人员")
                    }
                    console.log(response)
                })
                .finally(() => {
                    this.loading = false;
                    $('#editCourse').modal('hide');
                    this.clearEditModal();
                })
        },


        updateModalOn(course) {
            this.isUpdate = true;
            this.updateIdCourse = course.idCourse;
            this.updateChineseName = course.chineseName;
            this.updateBianHao = course.BianHao;
            this.updateIntro = course.intro;
            this.updateCredit = course.credit;
            this.updateSpring = course.spring;
            this.updateAutumn = course.autumn;
            this.updateSummer = course.summer;
            this.updateRequirement = course.xianxiu;
            this.updateYear = this.generateYear(course);
            this.updateDepartment = course.department;
            this.updateEnglishName = course.englishName;
            $('#editCourse').modal('show')
        },

        addModalOn() {
            this.isUpdate = false;
            this.clearEditModal();
            $('#editCourse').modal('show')
        },

        deleteModalOn(id) {
            this.deleteCourseId = id;
            $('#deleteModal').modal('show')
        },

        deleteCourse() {

            axios
                .post('/deletecourse',
                    {
                        course_id: this.deleteCourseId,
                        user_id: this.userId
                    })
                .then(response => {
                    if (response.data.state === "success") {
                        alert("删除成功")
                        this.loading = true;
                        axios
                            .post('/allcourse', {})
                            .then(response => {
                                this.courses = response.data
                                console.log(this.courses)
                            })
                            .catch(error => {
                                alert("课程信息接收错误， 请联系相关负责人员")
                            })
                            .finally(() => {
                                this.$forceUpdate();
                                this.loading = false;
                            })
                    } else if (response.data.state === "fail") {
                        alert("删除失败")
                    } else if (response.data.state === "not match"){
                        alert("您没有权限")
                    }
                    //console.log(response)
                })
                .catch(error => {
                    console.log(error)
                    alert("未知错误， 请联系相关负责人员")
                })
                .finally(() => {
                    $('#deleteModal').modal('hide')
                })
        },

        clearEditModal() {
            this.updateIdCourse = -1;
            this.updateChineseName = "";
            this.updateBianHao = "";
            this.updateIntro = "";
            this.updateCredit = 0;
            this.updateSpring = false;
            this.updateAutumn = false;
            this.updateSummer = false;
            this.updateIntro = "";
            this.updateYear = 1;
            this.updateDepartment = "";
            this.updateEnglishName = "";
        }

    },

    watch: {
        courses: {
            handler() {
                if (this.courses === null) {
                    this.courseNum = 0;
                } else {
                    this.courseNum = this.courses.length;
                }
            },
            immediate: true,
            deep: true
        },

        showTable: {
            handler(newShowTable, oldShowTable) {
                if (this.showTable === null) {
                    this.showNum = 0;
                } else {
                    this.showNum = this.showTable.length;
                }

                if (this.showNum === 0) {
                    this.totalPages = 1;
                } else {
                    this.totalPages = Math.ceil(this.showNum / 10);
                }
            },

            immediate: true,
            deep: true
        },

        currentYear: {
            handler() {
                this.loading = true;
                this.showTable.splice(0, this.showTable.length);
                for (var i = 0; i < this.courseNum; i++) {
                    console.log(1)
                    if (this.courses[i].department == this.currentDepart && this.courses[i].year == this.currentYear) {
                        this.showTable.push(this.courses[i]);
                    }
                }
                this.loading = false;
            },
            immediate: true,
        },

        currentDepart: {
            handler() {
                this.loading = true;
                this.showTable.splice(0, this.showTable.length);
                console.log(this.showTable);
                for (var i = 0; i < this.courseNum; i++) {
                    if (this.courses[i].department == this.currentDepart && this.courses[i].year == this.currentYear) {
                        this.showTable.push(this.courses[i]);
                    }
                }
                console.log(this.showTable)
                this.loading = false;
            },
            immediate: true
        }
    },

    computed: {

        pages() {
            const c = this.currentPage;
            const t = this.totalPages;
            if (t <= 10) {
                var foo = [];
                for (var i = 1; i <= t; i++) {
                    foo.push(i);
                }
                return foo;
            } else if (c <= 5) {
                return [1, 2, 3, 4, 5, 6, 7, 8, 9, '...', t]
            } else if (c >= t - 4) {
                return [1, '...', t - 8, t - 7, t - 6, t - 5, t - 4, t - 3, t - 2, t - 1, t]
            } else {
                return [1, '...', c - 3, c - 2, c - 1, c, c + 1, c + 2, c + 3, '...', t]
            }
        },

        computeIndex() {
            const c = this.currentPage;
            var s = (c - 1) * 10;
            var e = (c - 1) * 10 + 9;
            if (e > this.showTable.length - 1) {
                e = this.showTable.length - 1;
            }
            var foo = [];
            for (var i = s; i <= e; i++) {
                foo.push(i);
            }

            return foo
        },

        // setShowTable() {
        //     const _year = this.currentYear;
        //     const _major = this.currentMajor;
        //     const courseList = this.courses;
        //     for (course in courseList) {
        //         if (course.major === _major && course.year === _year) {
        //             this.showTable.push(course);
        //         }
        //     }
        // }
    },
    mounted: function () {
        this.loading = true;
        axios
            .post('/allcourse', {})
            .then(response => {
                this.courses = response.data
                console.log(this.courses)
            })
            .catch(error => {
                alert("课程信息接收错误， 请联系相关负责人员")
            })
            .finally(() => {
                this.loading = false;
            })

        axios
            .post("/department/findAllDepartmentAndMajor", {userId: 11711335})
            .then(response => {
                const departArr = response.data;
                console.log(departArr)
                for (var i = 0; i < departArr.length; i++) {
                    this.departments.push(departArr[i].name)
                }
                console.log(this.departments)
            })
            .catch(error => {
                console.log(error)
                alert("未知错误， 请联系相关负责人员")
            })
            .finally(() => {
                this.loadingDepartment = false;
            })

    },

})
