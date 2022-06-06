package com.htlimst.lieferrex;

import com.htlimst.lieferrex.model.Bestellung;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

import java.util.List;

@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
public class LieferrexApplication {



    public static void main(String[] args) {

        SpringApplication.run(LieferrexApplication.class, args);
    }

}
