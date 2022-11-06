
CREATE OR REPLACE FUNCTION public.func_delete_student(stu_id integer)
    RETURNS integer
    LANGUAGE plpgsql
    NOT FENCED NOT SHIPPABLE
AS $$

DECLARE

	/*declaration_section*/

BEGIN
  DELETE FROM fail_student WHERE studentid=stu_id;
  DELETE FROM score WHERE studentid=stu_id;
  DELETE FROM student WHERE studentid=stu_id;
	/*executable_section*/
  RETURN TRUE;
END;$$;
/

-- Name: func_delete_teacher; Type: Function; Schema: public;

CREATE OR REPLACE FUNCTION public.func_delete_teacher()
    RETURNS trigger
    LANGUAGE plpgsql
    NOT FENCED NOT SHIPPABLE
AS $$
DECLARE
BEGIN
    update student set teacherid=NULL where teacherid=OLD.teacherid;
    RETURN OLD;
END;
$$;
/

-- Name: func_insert2fail_student; Type: Function; Schema: public;

CREATE OR REPLACE FUNCTION public.func_insert2fail_student()
    RETURNS trigger
    LANGUAGE plpgsql
    NOT FENCED NOT SHIPPABLE
AS $$
DECLARE
BEGIN
    INSERT INTO fail_student VALUES(NEW.examCode, NEW.studentId, NEW.etscore);
    RETURN NEW;
END;
$$;
/

-- Name: prd_add_question; Type: Function; Schema: public;

CREATE OR REPLACE PROCEDURE public.prd_add_question(question_type integer, q_subject character varying, q_question character varying, q_analysis character varying, q_level character varying, q_section character varying, q_answer character varying, q_ansa character varying, q_ansb character varying, q_ansc character varying, q_ansd character varying)
AS
DECLARE

/*declaration_section*/

BEGIN
IF question_type=1 THEN
INSERT INTO fill_question(subject, question, answer, analysis, section, level)
VALUES(q_subject,q_question,q_answer,q_analysis,q_section,q_level);
ELSIF question_type=2 THEN
INSERT INTO judge_question(subject, question, answer, analysis, section, level)
VALUES(q_subject,q_question,q_answer,q_analysis,q_section,q_level);
ELSE
INSERT INTO multi_question(subject,question,answerA,answerB,answerC,answerD,rightAnswer,analysis,section,level)
VALUES(q_subject,q_question,q_ansA,q_ansB,q_ansC,q_ansD,q_answer,q_analysis,q_section,q_level);
END IF;
/*executable_section*/

END;
/
/

-- Name: prd_admin_delete; Type: Function; Schema: public;

CREATE OR REPLACE PROCEDURE public.prd_admin_delete(admin_id integer)
AS
DECLARE

    /*declaration_section*/

BEGIN
DELETE FROM admin WHERE adminId=admin_id;
/*executable_section*/

END;
/
/

-- Name: prd_delete_exam; Type: Function; Schema: public;

CREATE OR REPLACE PROCEDURE public.prd_delete_exam(exam_id integer)
AS
DECLARE

    /*declaration_section*/

    BEGIN
DELETE FROM exam_manage WHERE examCode=exam_id;
/*executable_section*/

END;
/
/

-- Name: prd_insert_update_admin; Type: Function; Schema: public;

CREATE OR REPLACE PROCEDURE public.prd_insert_update_admin(a_id integer, a_name character varying, a_sex character varying, a_tel character varying, a_email character varying, a_pwd character varying, a_cardid character varying, a_role character varying)
AS
DECLARE
    /*declaration_section*/
    CURSOR cr_admin IS
SELECT adminid FROM admin WHERE adminId = a_id;
/*SELECT studentname, grade, major, clazz, institute, tel, eamil, pwd, cardid, sex, role FROM student WHERE studentId = stu_id;*/
id INTEGER;

BEGIN
OPEN cr_admin;
FETCH cr_admin INTO id;
IF cr_admin%NOTFOUND THEN
INSERT INTO admin(adminname, sex, tel, eamil, pwd, cardId, role)
VALUES(a_name, a_sex, a_tel, a_email, a_pwd, a_cardId, a_role);
ELSE
UPDATE admin SET adminName=a_name, sex=a_sex, tel=a_tel, email=a_email, pwd=a_pwd, cardId=a_cardId, role=a_role
WHERE adminId=a_id;
END IF;
CLOSE cr_admin;
/*executable_section*/
END;
/
/

-- Name: prd_insert_update_exam; Type: Function; Schema: public;

CREATE OR REPLACE PROCEDURE public.prd_insert_update_exam(e_id integer, e_description character varying, e_source character varying, e_paperid integer, e_examdate character varying, e_totaltime integer, e_grade character varying, e_term character varying, e_major character varying, e_institute character varying, e_totalscore integer, e_type character varying, e_tips character varying)
AS
DECLARE
/*declaration_section*/
    CURSOR c_exam IS SELECT examCode
    FROM exam_manage
    WHERE examCode = e_id;
/*SELECT studentname, grade, major, clazz, institute, tel, eamil, pwd, cardid, sex, role FROM student WHERE studentId = stu_id;*/
id INTEGER;
BEGIN
OPEN c_exam;
FETCH c_exam INTO id;
IF c_exam % NOTFOUND THEN
INSERT INTO exam_manage
(description, source, paperId, examDate, totalTime, grade, term, major, institute, totalScore, type, tips)
VALUES
    (e_description, e_source, e_paperid, e_examdate, e_totaltime, e_grade, e_term, e_major, e_institute, e_totalscore, e_type, e_tips);
ELSE
UPDATE exam_manage
SET description = e_description, source = e_source, paperId = e_paperid, examDate = e_examdate, totalTime = e_totaltime, grade = e_grade, term = e_term, major = e_major, institute = e_institute, totalScore = e_totalscore, type = e_type, tips = e_tips
WHERE examCode = e_id;
END IF;
CLOSE c_exam;
/*executable_section*/
END;
/
/

-- Name: prd_insert_update_message; Type: Function; Schema: public;

CREATE OR REPLACE PROCEDURE public.prd_insert_update_message(m_id integer, m_title character varying, m_content character varying, m_time character varying)
AS
DECLARE
    CURSOR c1 IS
SELECT id FROM message WHERE id = m_id;
/*declaration_section*/
tmp_id INTEGER;
BEGIN
OPEN c1;
FETCH c1 into tmp_id;
IF c1 % NOTFOUND THEN
INSERT INTO message(title, content, time) VALUES(m_title, m_content, m_time);
ELSE
UPDATE message SET title=m_title, content=m_content, time=m_time WHERE id=m_id;
/*executable_section*/
END IF;
CLOSE c1;
END;
/
/

-- Name: prd_insert_update_reply; Type: Function; Schema: public;

CREATE OR REPLACE PROCEDURE public.prd_insert_update_reply(r_id integer, m_id integer, r_content character varying, r_time character varying)
AS
DECLARE
    CURSOR c1 IS
SELECT replayid FROM replay WHERE replayid = r_id;
/*declaration_section*/
tmp_id INTEGER;
BEGIN
OPEN c1;
FETCH c1 into tmp_id;
IF c1 % NOTFOUND THEN
INSERT INTO replay(messageId, replay, replayTime) VALUES(m_id, r_content, r_time);
ELSE
UPDATE replay SET replay=r_content, replayTime=r_time WHERE replayid=r_id;
/*executable_section*/
END IF;
CLOSE c1;
END;
/
/

-- Name: prd_insert_update_student; Type: Function; Schema: public;

CREATE OR REPLACE PROCEDURE public.prd_insert_update_student(stu_id integer, stu_name character varying,stu_image character varying, stu_grade character varying, stu_major character varying, stu_class character varying, stu_institute character varying, stu_tel character varying, stu_email character varying, stu_pwd character varying, stu_cardid character varying, stu_sex character varying, stu_role character varying, teacher_id integer)
AS
DECLARE
    /*declaration_section*/
    CURSOR cr_stu1 IS
SELECT studentid FROM student WHERE studentId = stu_id;
/*SELECT studentname, grade, major, clazz, institute, tel, eamil, pwd, cardid, sex, role FROM student WHERE studentId = stu_id;*/
s_id INTEGER;

BEGIN
OPEN cr_stu1;
FETCH cr_stu1 INTO s_id;
IF cr_stu1%NOTFOUND THEN
INSERT INTO student(studentname, image, grade, major, clazz, institute, tel,
                    email, pwd, cardid, sex, role, teacherid)
VALUES(stu_name, stu_image, stu_grade, stu_major, stu_class, stu_institute, stu_tel,
       stu_email, stu_pwd, stu_cardId, stu_sex, stuVALUE, teacher_id);
ELSE
UPDATE student SET studentname=stu_name, image=stu_image, grade=stu_grade, major=stu_major, clazz=stu_class, institute=stu_institute, tel=stu_tel,
                   email=stu_email, pwd=stu_pwd, cardId=stu_cardId, sex=stu_sex, role=stu_role, teacherid=teacher_id
WHERE studentId=stu_id;
END IF;
CLOSE cr_stu1;
/*executable_section*/
END;
/
/

-- Name: prd_insert_update_teacher; Type: Function; Schema: public;

CREATE OR REPLACE PROCEDURE public.prd_insert_update_teacher(t_id integer, t_name character varying, t_sex character varying, t_tel character varying, t_email character varying, t_pwd character varying, t_cardid character varying, t_role character varying, t_type character varying, t_institute character varying)
AS
DECLARE
    /*declaration_section*/
    CURSOR c_tea IS
SELECT teacherid FROM teacher WHERE teacherId = t_id;
/*SELECT studentname, grade, major, clazz, institute, tel, eamil, pwd, cardid, sex, role FROM student WHERE studentId = stu_id;*/
id INTEGER;

BEGIN
OPEN c_tea;
FETCH c_tea INTO id;
IF c_tea%NOTFOUND THEN
INSERT INTO teacher(teachername, sex, tel, email, pwd, cardId, role, type, institute)
VALUES(t_name, t_sex, t_tel, t_email, t_pwd, t_cardId, t_role, t_type, t_institute);
ELSE
UPDATE teacher SET teachername=t_name, sex=t_sex, tel=t_tel, email=t_email, pwd=t_pwd, cardId=t_cardId, role=t_role, type=t_type,
                   institute=t_institute
WHERE teacherId=t_id;
END IF;
CLOSE c_tea;
/*executable_section*/
END;
/
/

-- Name: prd_login_admin; Type: Function; Schema: public;

CREATE OR REPLACE PROCEDURE public.prd_login_admin(user_id integer, user_pwd character varying, OUT uid integer, OUT uname character varying, OUT usex character varying, OUT utel character varying, OUT uemail character varying, OUT ucardid character varying, OUT urole character varying)
AS
DECLARE
/*declaration_section*/
    CURSOR a_cursor IS SELECT adminId, adminName, sex, tel, email, cardId, role
    FROM admin
    WHERE adminId = user_id
    AND pwd = user_pwd;
BEGIN
OPEN a_cursor;
FETCH a_cursor INTO uid, uname, usex, utel, uemail, ucardId, urole;
CLOSE a_cursor;
/*executable_section*/
END;
/
/