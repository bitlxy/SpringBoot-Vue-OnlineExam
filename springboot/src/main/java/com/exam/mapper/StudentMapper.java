package com.exam.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.exam.entity.Student;
import com.exam.entity.Student1;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.StatementType;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Mapper
public interface StudentMapper {

    /**
     * 分页查询所有学生
     * @param page
     * @return List<Student>
     */
    // @Select("select * from student ")
    @Select("select student.*, teacher.teacherName from student left join teacher on student.teacherId = teacher.teacherId")
    IPage<Student1> findAll(Page page);

    @Select("select student.*, teacher.teacherName from student left join teacher on student.teacherId = teacher.teacherId")
    List<Student1> findAll();

    @Select("select * from student where studentId = #{studentId}")
    Student findById(Integer studentId);

    @Delete("{call func_delete_student(#{studentId, mode=IN, jdbcType=INTEGER})}")
    // 因为多个表中会存在该学生的信息
    @Options(statementType = StatementType.CALLABLE)
    int deleteById(Integer studentId);

    /**
     * 更新所有学生信息
     * @param student 传递一个对象
     * @return 受影响的记录条数
     */

    @Update("{call prd_insert_update_student(#{studentId,mode=IN,jdbcType=INTEGER}, #{studentName,mode=IN,jdbcType=VARCHAR},#{image, mode=IN, jdbcType=VARCHAR},#{grade,mode=IN,jdbcType=VARCHAR}," +
            "#{major,mode=IN,jdbcType=VARCHAR}, #{clazz,mode=IN,jdbcType=VARCHAR}, #{institute,mode=IN,jdbcType=VARCHAR}, #{tel,mode=IN,jdbcType=VARCHAR}," +
            "#{email,mode=IN,jdbcType=VARCHAR}, #{pwd,mode=IN,jdbcType=VARCHAR}, #{cardId,mode=IN,jdbcType=VARCHAR},#{sex,mode=IN,jdbcType=VARCHAR}," +
            "#{role,mode=IN,jdbcType=VARCHAR},#{teacherId,mode=IN,jdbcType=INTEGER})}")
    @Options(statementType = StatementType.CALLABLE)
    int update(Student student);

    /**
     * 更新密码
     * @param student
     * @return 受影响的记录条数
     */
    @Update("update student set pwd = #{pwd} where studentId = #{studentId}")
    int updatePwd(Student student);


    //@Options(useGeneratedKeys = true,keyProperty = "studentId")
    //@Insert("insert into student(studentName,grade,major,clazz,institute,tel,email,pwd,cardId,sex,role) values " +
    //       "(#{studentName},#{grade},#{major},#{clazz},#{institute},#{tel},#{email},#{pwd},#{cardId},#{sex},#{role})")
    @Options(useGeneratedKeys = true, keyProperty = "studentId", statementType = StatementType.CALLABLE)
    @Insert("{call prd_insert_update_student(#{studentId,mode=IN,jdbcType=INTEGER}, #{studentName,mode=IN,jdbcType=VARCHAR},#{image, mode=IN, jdbcType=VARCHAR\"#{grade,mode=IN,jdbcType=VARCHAR}," +
            "#{major,mode=IN,jdbcType=VARCHAR}, #{clazz,mode=IN,jdbcType=VARCHAR}, #{institute,mode=IN,jdbcType=VARCHAR}, #{tel,mode=IN,jdbcType=VARCHAR}," +
            "#{email,mode=IN,jdbcType=VARCHAR}, #{pwd,mode=IN,jdbcType=VARCHAR}, #{cardId,mode=IN,jdbcType=VARCHAR},#{sex,mode=IN,jdbcType=VARCHAR}," +
            "#{role,mode=IN,jdbcType=VARCHAR}, #{teacherId,mode=IN,jdbcType=INTEGER})}")
    int add(Student student);

    @Select("select * from student")
    List<Student> findAllStudent();
}