package com.htlimst.lieferrex;

import com.htlimst.lieferrex.model.*;
import com.htlimst.lieferrex.repository.AngestellterRepository;
import com.htlimst.lieferrex.repository.MandantRepository;
import com.htlimst.lieferrex.repository.RolleRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.boot.CommandLineRunner;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Set;
import java.util.Date.*;

@SpringBootApplication
public class LieferrexApplication {

    public static void main(String[] args) {
        SpringApplication.run(LieferrexApplication.class, args);
    }

}
