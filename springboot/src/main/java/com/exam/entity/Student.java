package com.exam.entity;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Data
@Setter
@Getter
public class Student {
    @ExcelIgnore
    private Object FillPatternType;

    @ColumnWidth(40)
    @ExcelProperty("学号")
    private Integer studentId;

    @ColumnWidth(20)
    @ExcelProperty("姓名")
    private String studentName;

    @ExcelIgnore
    private String image;

    @ColumnWidth(20)
    @ExcelProperty("年级")
    private String grade;

    @ColumnWidth(20)
    @ExcelProperty("专业")
    private String major;

    @ColumnWidth(20)
    @ExcelProperty("班级")
    private String clazz;

    @ColumnWidth(20)
    @ExcelProperty("学院")
    private String institute;

    @ColumnWidth(40)
    @ExcelProperty("电话号码")
    private String tel;

    @ColumnWidth(40)
    @ExcelProperty("邮件")
    private String email;

    @ExcelIgnore
    private String pwd;

    @ColumnWidth(50)
    @ExcelProperty("身份证号")
    private String cardId;

    @ColumnWidth(20)
    @ExcelProperty("性别")
    private String sex;

    @ColumnWidth(30)
    @ExcelProperty("指导教师ID")
    private String teacherId;

    @ExcelIgnore
    private String role;
}