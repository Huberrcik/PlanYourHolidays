package com.PlanYourHolidays;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
interface FlightsRepository extends JpaRepository <LaunchGoogleFlights, Integer >{

}
