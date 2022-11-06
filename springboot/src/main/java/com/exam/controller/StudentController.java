package com.exam.controller;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.util.MapUtils;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.exam.entity.ApiResult;
import com.exam.entity.Student;
import com.exam.entity.Student1;
import com.exam.serviceimpl.StudentServiceImpl;
import com.exam.util.ApiResultHandler;
import com.huawei.shade.com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

@RestController
public class StudentController {

    @Autowired
    private StudentServiceImpl studentService;

    // ok?
    @GetMapping("/students/{page}/{size}")
    public ApiResult findAll(@PathVariable Integer page, @PathVariable Integer size) {
        Page<Student1> studentPage = new Page<>(page, size);
        IPage<Student1> res = studentService.findAll(studentPage);
        return ApiResultHandler.buildApiResult(200, "分页查询所有学生", res);
    }

    // 这个是修改学生信息时先获取的，不用改
    @GetMapping("/student/{studentId}")
    public ApiResult findById(@PathVariable("studentId") Integer studentId) throws SQLException {
        Student res = studentService.findById(studentId);
        if (res != null) {
            return ApiResultHandler.buildApiResult(200, "请求成功", res);
        } else {
            return ApiResultHandler.buildApiResult(404, "查询的用户不存在", null);
        }
    }

    // ok
    @DeleteMapping("/student/{studentId}")
    public ApiResult deleteById(@PathVariable("studentId") Integer studentId) {
        return ApiResultHandler.buildApiResult(200, "删除成功", studentService.deleteById(studentId));
    }

    // ok
    @PutMapping("/studentPWD")
    public ApiResult updatePwd(@RequestBody Student student) {
        studentService.updatePwd(student);
        return ApiResultHandler.buildApiResult(200, "密码更新成功", null);
    }

    // ok?
    @PutMapping("/student")
    public ApiResult update(@RequestBody Student student) {
        int res = studentService.update(student);
        if (res != 0) {
            return ApiResultHandler.buildApiResult(200, "更新成功", res);
        }
        return ApiResultHandler.buildApiResult(400, "更新失败", res);
    }

    // ok?
    @PostMapping("/student")
    public ApiResult add(@RequestBody Student student) {
        int res = studentService.add(student);
        if (res == 1) {
            return ApiResultHandler.buildApiResult(200, "添加成功", null);
        } else {
            return ApiResultHandler.buildApiResult(400, "添加失败", null);
        }
    }

    @PostMapping("/exportStudent")
    public ApiResult exportStudent() {
        int res = studentService.exportStudent();
        if (res == 1) {
            return ApiResultHandler.buildApiResult(200, "导出成功", null);
        } else {
            return ApiResultHandler.buildApiResult(400, "导出失败", null);
        }
    }

    @GetMapping("/student/export")
    public ApiResult downloadFailedUsingJson(HttpServletResponse response) throws IOException {
        // 这里注意 有同学反应使用swagger 会导致各种问题，请直接用浏览器或者用postman
        List<Student> dataList = studentService.findAllStudent();
        try {
            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            response.setCharacterEncoding("utf-8");
            // 这里URLEncoder.encode可以防止中文乱码 当然和easyexcel没有关系
            String fileName = "studentExport.xlsx";
            response.setHeader("Content-disposition", "attachment;filename=" + URLEncoder.encode(fileName));
            // 这里需要设置不关闭流
            EasyExcel.write(response.getOutputStream(), Student.class).autoCloseStream(Boolean.FALSE).sheet("学生信息")
                    .doWrite(dataList);
            return ApiResultHandler.buildApiResult(200, "成功导出文件", 200);
        } catch (Exception e) {
            // 重置response
            response.reset();
            response.setContentType("application/json");
            response.setCharacterEncoding("utf-8");
            Map<String, String> map = MapUtils.newHashMap();
            map.put("status", "failure");
            map.put("message", "下载文件失败" + e.getMessage());
            response.getWriter().println(JSON.toJSONString(map));
            return ApiResultHandler.buildApiResult(400, "导出文件失败", 400);
        }
    }

    @PostMapping("/student/upload")
    public ApiResult upload(@RequestPart MultipartFile file, @RequestParam("path") String path, @RequestParam("studentId") Integer studentId) {
        String fullPath = studentService.upload(file, path, studentId);
        return ApiResultHandler.buildApiResult(200, "添加成功", fullPath);
    }

    @GetMapping("/student/getImage")
    public ApiResult getImage(@RequestParam Integer studentId) throws SQLException {
        Student byId = studentService.findById(studentId);
        return ApiResultHandler.buildApiResult(200, "查找成功", byId.getImage());
    }

}
