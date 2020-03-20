package com.mouxianyu.studentsociety;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
@MapperScan("com.mouxianyu.studentsociety.mapper")
public class StudentSocietyApplication {

    public static void main(String[] args) {
        SpringApplication.run(StudentSocietyApplication.class, args);
    }

}
