package com.burakkarahan.WeatherTurkey.service;

import com.burakkarahan.WeatherTurkey.entity.City;
import com.burakkarahan.WeatherTurkey.entity.Region;
import com.burakkarahan.WeatherTurkey.repository.CityRepository;
import com.burakkarahan.WeatherTurkey.repository.RegionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor // final olan alanlar için otomatik constructor oluşturur (Lombok)
public class DataSeedingService implements CommandLineRunner {

    // Veritabanı işlemleri için gerekli repository'leri enjekte ediyoruz.
    private final RegionRepository regionRepository;
    private final CityRepository cityRepository;

    /**
     * Bu metot, Spring Boot uygulaması tamamen ayağa kalktığında OTOMATİK olarak
     * ve SADECE BİR KEZ çalıştırılır.
     */
    @Override
    public void run(String... args) throws Exception {
        // Eğer veritabanında hiç bölge yoksa, coğrafi verileri yükle.
        // Bu kontrol, uygulama her yeniden başladığında verilerin tekrar tekrar eklenmesini önler.
        if (regionRepository.count() == 0) {
            System.out.println("Veritabanı boş, coğrafi veriler yükleniyor...");
            loadRegionsAndCities();
            System.out.println("Coğrafi veriler başarıyla yüklendi.");
        } else {
            System.out.println("Veritabanında zaten veri mevcut, yükleme atlanıyor.");
        }
    }

    private void loadRegionsAndCities() {
        // 1. Bölgeleri Oluştur ve Kaydet
        Region marmara = regionRepository.save(Region.builder().name("Marmara Bölgesi").build());
        Region ege = regionRepository.save(Region.builder().name("Ege Bölgesi").build());
        Region icAnadolu = regionRepository.save(Region.builder().name("İç Anadolu Bölgesi").build());
        Region akdeniz = regionRepository.save(Region.builder().name("Akdeniz Bölgesi").build());
        Region karadeniz = regionRepository.save(Region.builder().name("Karadeniz Bölgesi").build());
        Region doguAnadolu = regionRepository.save(Region.builder().name("Doğu Anadolu Bölgesi").build());
        Region guneydoguAnadolu = regionRepository.save(Region.builder().name("Güneydoğu Anadolu Bölgesi").build());

        // 2. Şehirleri Oluştur ve Kaydet
        // Her şehir, ait olduğu bölge ile ilişkilendirilir.
        List<City> cities = Arrays.asList(
                // Akdeniz Bölgesi
                City.builder().name("Adana").plateCode("01").region(akdeniz).build(),
                City.builder().name("Antalya").plateCode("07").region(akdeniz).build(),
                City.builder().name("Burdur").plateCode("15").region(akdeniz).build(),
                City.builder().name("Hatay").plateCode("31").region(akdeniz).build(),
                City.builder().name("Isparta").plateCode("32").region(akdeniz).build(),
                City.builder().name("Mersin").plateCode("33").region(akdeniz).build(),
                City.builder().name("Kahramanmaraş").plateCode("46").region(akdeniz).build(),
                City.builder().name("Osmaniye").plateCode("80").region(akdeniz).build(),

                // Doğu Anadolu Bölgesi
                City.builder().name("Ağrı").plateCode("04").region(doguAnadolu).build(),
                City.builder().name("Ardahan").plateCode("75").region(doguAnadolu).build(),
                City.builder().name("Bingöl").plateCode("12").region(doguAnadolu).build(),
                City.builder().name("Bitlis").plateCode("13").region(doguAnadolu).build(),
                City.builder().name("Elazığ").plateCode("23").region(doguAnadolu).build(),
                City.builder().name("Erzincan").plateCode("24").region(doguAnadolu).build(),
                City.builder().name("Erzurum").plateCode("25").region(doguAnadolu).build(),
                City.builder().name("Hakkâri").plateCode("30").region(doguAnadolu).build(),
                City.builder().name("Iğdır").plateCode("76").region(doguAnadolu).build(),
                City.builder().name("Kars").plateCode("36").region(doguAnadolu).build(),
                City.builder().name("Malatya").plateCode("44").region(doguAnadolu).build(),
                City.builder().name("Muş").plateCode("49").region(doguAnadolu).build(),
                City.builder().name("Tunceli").plateCode("62").region(doguAnadolu).build(),
                City.builder().name("Van").plateCode("65").region(doguAnadolu).build(),

                // Ege Bölgesi
                City.builder().name("Afyonkarahisar").plateCode("03").region(ege).build(),
                City.builder().name("Aydın").plateCode("09").region(ege).build(),
                City.builder().name("Denizli").plateCode("20").region(ege).build(),
                City.builder().name("İzmir").plateCode("35").region(ege).build(),
                City.builder().name("Kütahya").plateCode("43").region(ege).build(),
                City.builder().name("Manisa").plateCode("45").region(ege).build(),
                City.builder().name("Muğla").plateCode("48").region(ege).build(),
                City.builder().name("Uşak").plateCode("64").region(ege).build(),

                // Güneydoğu Anadolu Bölgesi
                City.builder().name("Adıyaman").plateCode("02").region(guneydoguAnadolu).build(),
                City.builder().name("Batman").plateCode("72").region(guneydoguAnadolu).build(),
                City.builder().name("Diyarbakır").plateCode("21").region(guneydoguAnadolu).build(),
                City.builder().name("Gaziantep").plateCode("27").region(guneydoguAnadolu).build(),
                City.builder().name("Kilis").plateCode("79").region(guneydoguAnadolu).build(),
                City.builder().name("Mardin").plateCode("47").region(guneydoguAnadolu).build(),
                City.builder().name("Siirt").plateCode("56").region(guneydoguAnadolu).build(),
                City.builder().name("Şanlıurfa").plateCode("63").region(guneydoguAnadolu).build(),
                City.builder().name("Şırnak").plateCode("73").region(guneydoguAnadolu).build(),

                // İç Anadolu Bölgesi
                City.builder().name("Aksaray").plateCode("68").region(icAnadolu).build(),
                City.builder().name("Ankara").plateCode("06").region(icAnadolu).build(),
                City.builder().name("Çankırı").plateCode("18").region(icAnadolu).build(),
                City.builder().name("Eskişehir").plateCode("26").region(icAnadolu).build(),
                City.builder().name("Karaman").plateCode("70").region(icAnadolu).build(),
                City.builder().name("Kayseri").plateCode("38").region(icAnadolu).build(),
                City.builder().name("Kırıkkale").plateCode("71").region(icAnadolu).build(),
                City.builder().name("Kırşehir").plateCode("40").region(icAnadolu).build(),
                City.builder().name("Konya").plateCode("42").region(icAnadolu).build(),
                City.builder().name("Nevşehir").plateCode("50").region(icAnadolu).build(),
                City.builder().name("Niğde").plateCode("51").region(icAnadolu).build(),
                City.builder().name("Sivas").plateCode("58").region(icAnadolu).build(),
                City.builder().name("Yozgat").plateCode("66").region(icAnadolu).build(),

                // Karadeniz Bölgesi
                City.builder().name("Amasya").plateCode("05").region(karadeniz).build(),
                City.builder().name("Artvin").plateCode("08").region(karadeniz).build(),
                City.builder().name("Bartın").plateCode("74").region(karadeniz).build(),
                City.builder().name("Bayburt").plateCode("69").region(karadeniz).build(),
                City.builder().name("Bolu").plateCode("14").region(karadeniz).build(),
                City.builder().name("Çorum").plateCode("19").region(karadeniz).build(),
                City.builder().name("Düzce").plateCode("81").region(karadeniz).build(),
                City.builder().name("Giresun").plateCode("28").region(karadeniz).build(),
                City.builder().name("Gümüşhane").plateCode("29").region(karadeniz).build(),
                City.builder().name("Karabük").plateCode("78").region(karadeniz).build(),
                City.builder().name("Kastamonu").plateCode("37").region(karadeniz).build(),
                City.builder().name("Ordu").plateCode("52").region(karadeniz).build(),
                City.builder().name("Rize").plateCode("53").region(karadeniz).build(),
                City.builder().name("Samsun").plateCode("55").region(karadeniz).build(),
                City.builder().name("Sinop").plateCode("57").region(karadeniz).build(),
                City.builder().name("Tokat").plateCode("60").region(karadeniz).build(),
                City.builder().name("Trabzon").plateCode("61").region(karadeniz).build(),
                City.builder().name("Zonguldak").plateCode("67").region(karadeniz).build(),

                // Marmara Bölgesi
                City.builder().name("Balıkesir").plateCode("10").region(marmara).build(),
                City.builder().name("Bilecik").plateCode("11").region(marmara).build(),
                City.builder().name("Bursa").plateCode("16").region(marmara).build(),
                City.builder().name("Çanakkale").plateCode("17").region(marmara).build(),
                City.builder().name("Edirne").plateCode("22").region(marmara).build(),
                City.builder().name("İstanbul").plateCode("34").region(marmara).build(),
                City.builder().name("Kırklareli").plateCode("39").region(marmara).build(),
                City.builder().name("Kocaeli").plateCode("41").region(marmara).build(),
                City.builder().name("Sakarya").plateCode("54").region(marmara).build(),
                City.builder().name("Tekirdağ").plateCode("59").region(marmara).build(),
                City.builder().name("Yalova").plateCode("77").region(marmara).build()
        );

        cityRepository.saveAll(cities); // Tüm şehirleri tek bir işlemle veritabanına kaydet
    }
}
