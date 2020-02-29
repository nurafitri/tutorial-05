package com.apap.tu05.service;

import java.util.List;

import com.apap.tu05.model.FlightModel;

public interface FlightService {
	FlightModel getFlightDetailByFlightNumber(String flightNumber);

	void addFlight(FlightModel flight);

	List<FlightModel> getAllFlight();

	void deleteFlight(String flightNumber);

	void updateFlight(String flightNumber, FlightModel flight);
}