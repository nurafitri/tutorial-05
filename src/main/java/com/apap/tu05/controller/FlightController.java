package com.apap.tu05.controller;

import java.util.ArrayList;
import java.util.List;

import com.apap.tu05.model.FlightModel;
import com.apap.tu05.model.PilotModel;
import com.apap.tu05.service.FlightService;
import com.apap.tu05.service.PilotService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class FlightController {
	@Autowired
	private FlightService flightService;

	@Autowired
	private PilotService pilotService;

	@RequestMapping(value = "/flight/add/{licenseNumber}", method = RequestMethod.GET)
	private String add(@PathVariable(value = "licenseNumber") String licenseNumber, Model model) {
		FlightModel flight = new FlightModel();
		PilotModel pilot = pilotService.getPilotDetailByLicenseNumber(licenseNumber);
		ArrayList<FlightModel> pilotFlight = new ArrayList<>();
		pilotFlight.add(flight);
		pilot.setPilotFlight(pilotFlight);
		flight.setPilot(pilot);
		String navigation = "ADD FLIGHT";
		model.addAttribute("navigation", navigation);
		model.addAttribute("pilot", pilot);
		model.addAttribute("flight", flight);
		return "addFlight";
	}

	@RequestMapping(value = "/flight/add/{licenseNumber}", method = RequestMethod.POST, params = { "addRow" })
	public String addRow(@ModelAttribute PilotModel pilot, BindingResult bindingResult, Model model) {
		if (pilot.getPilotFlight() == null) {
			pilot.setPilotFlight(new ArrayList<FlightModel>());
		}
		String navigation = "ADD FLIGHT";
		model.addAttribute("navigation", navigation);
		FlightModel flightBaru = new FlightModel();
		pilot.getPilotFlight().add(flightBaru);
		model.addAttribute("pilot", pilot);
		return "addFlight";
	}

	@RequestMapping(value = "/flight/add/{licenseNumber}", method = RequestMethod.POST, params = { "submit" })
	private String addFlightSubmit(@ModelAttribute PilotModel pilot) {
		PilotModel currentPilot = pilotService.getPilotDetailByLicenseNumber(pilot.getLicenseNumber());
		for (FlightModel flight : pilot.getPilotFlight()) {
			flight.setPilot(currentPilot);
			flightService.addFlight(flight);
		}
		return "add";
	}

	@RequestMapping(value = "/flight/delete/{flightNumber}", method = RequestMethod.GET)
	private String deleteFlight(@PathVariable(value = "flightNumber") String flightNumber, Model model) {
		flightService.deleteFlight(flightNumber);
		return "delete-flight";
	}

	@RequestMapping(value = "/flight/delete", method = RequestMethod.POST)
	private String deleteFlightById(@ModelAttribute PilotModel pilot, Model model) {
		for (FlightModel flight : pilot.getPilotFlight()) {
			flightService.deleteFlightById(flight.getId());
		}
		return "delete-flight";
	}

	@RequestMapping(value = "/flight/update/{flightNumber}", method = RequestMethod.GET)
	private String updateFlight(@PathVariable(value = "flightNumber") String flightNumber, Model model) {
		FlightModel flight = flightService.getFlightDetailByFlightNumber(flightNumber);
		model.addAttribute("flight", flight);
		model.addAttribute("updatedFlight", new FlightModel());
		return "update-flight";
	}

	@RequestMapping(value = "/flight/update/{flightNumber}", method = RequestMethod.POST)
	private String updateFlightSubmit(@ModelAttribute FlightModel updatedFlight,
			@PathVariable(value = "flightNumber") String flightNumber, Model model) {
		flightService.updateFlight(flightNumber, updatedFlight);
		return "updated";
	}

	@RequestMapping(value = "/flights", method = RequestMethod.GET)
	private String viewFlights(Model model) {
		List<FlightModel> flights = flightService.getAllFlight();
		String navigation = "FLIGHTS";
		model.addAttribute("navigation", navigation);
		model.addAttribute("flights", flights);
		return "flights";
	}
}