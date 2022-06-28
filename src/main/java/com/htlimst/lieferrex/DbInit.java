  package com.htlimst.lieferrex;


  import com.htlimst.lieferrex.model.*;
  import com.htlimst.lieferrex.model.enums.BestellartEnum;
  import com.htlimst.lieferrex.model.enums.BestellstatusEnum;
  import com.htlimst.lieferrex.model.enums.KategorieEnum;
  import com.htlimst.lieferrex.model.enums.WochentagEnum;
  import com.htlimst.lieferrex.model.fragments.FragmentHeader;
  import com.htlimst.lieferrex.model.fragments.FragmentMap;
  import com.htlimst.lieferrex.model.fragments.FragmentText;
  import com.htlimst.lieferrex.model.fragments.FragmentType;
  import com.htlimst.lieferrex.repository.*;
  import org.springframework.beans.factory.annotation.Autowired;
  import org.springframework.boot.CommandLineRunner;
  import org.springframework.security.crypto.password.PasswordEncoder;
  import org.springframework.stereotype.Service;

  import java.sql.Array;
  import java.sql.Time;
  import java.sql.Timestamp;
  import java.util.*;


  @Service
  public class DbInit implements CommandLineRunner {

      @Autowired
      private AngestellterRepository angestellterRepository;
      @Autowired
      private BestellartRepository bestellartRepository;
      @Autowired
      private BestellstatusRepository bestellstatusRepository;
      @Autowired
      private BestellungRepository bestellungRepository;
      @Autowired
      private FragmentHeaderRepository fragmentHeaderRepository;
      @Autowired
      private FragmentImageRepository fragmentImageRepository;
      @Autowired
      private FragmentMapRepository fragmentMapRepository;
      @Autowired
      private FragmentRepository fragmentRepository;
      @Autowired
      private FragmentTextRepository fragmentTextRepository;
      @Autowired
      private FragmentTypeRepository fragmentTypeRepository;
      @Autowired
      private GeoPositionRepository geoPositionRepository;
      @Autowired
      private GerichtBestellungRepository gerichtBestellungRepository;
      @Autowired
      private GerichtRepository gerichtRepository;
      @Autowired
      private KategorieRepository kategorieRepository;
      @Autowired
      private KundeRepository kundeRepository;
      @Autowired
      private LayoutRepository layoutRepository;
      @Autowired
      private MandantRepository mandantRepository;
      @Autowired
      private OeffnungszeitRepository oeffnungszeitRepository;
      @Autowired
      private PositionRepository positionRepository;
      @Autowired
      private RolleRepository rolleRepository;
      @Autowired
      private PasswordEncoder passwordEncoder;
      @Autowired
      private UmsatzRepository umsatzRepository;
      @Autowired
      private SeitenaufrufeRepository seitenaufrufeRepository;



      @Override
      public void run(String... args) {

          if (mandantRepository.count() > 0) {
              return;
          }
          deleteAll();



      }


      private void deleteAll() {
          angestellterRepository.deleteAll();
          bestellartRepository.deleteAll();
          bestellstatusRepository.deleteAll();
          bestellungRepository.deleteAll();
          fragmentHeaderRepository.deleteAll();
          fragmentImageRepository.deleteAll();
          fragmentMapRepository.deleteAll();
          fragmentRepository.deleteAll();
          fragmentTextRepository.deleteAll();
          fragmentTypeRepository.deleteAll();
          geoPositionRepository.deleteAll();
          gerichtBestellungRepository.deleteAll();
          gerichtRepository.deleteAll();
          kategorieRepository.deleteAll();
          kundeRepository.deleteAll();
          layoutRepository.deleteAll();
          mandantRepository.deleteAll();
          oeffnungszeitRepository.deleteAll();
          positionRepository.deleteAll();
          rolleRepository.deleteAll();
          umsatzRepository.deleteAll();
          seitenaufrufeRepository.deleteAll();
      }

  }
