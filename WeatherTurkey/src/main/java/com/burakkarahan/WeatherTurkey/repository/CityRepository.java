package com.burakkarahan.WeatherTurkey.repository;

import com.burakkarahan.WeatherTurkey.entity.City;
import com.burakkarahan.WeatherTurkey.entity.Region;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CityRepository extends JpaRepository<City, Long> {

    Optional<City> findByName(String name);

    List<City> findAllByRegion(Region region);
}

