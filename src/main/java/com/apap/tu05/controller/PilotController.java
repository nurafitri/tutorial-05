package com.apap.tu05.controller;

import com.apap.tu05.model.PilotModel;
import com.apap.tu05.service.PilotService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class PilotController {
	@Autowired
	private PilotService pilotService;

	@RequestMapping("/")
	private String home(Model model) {
		
		model.addAttribute("navigation", "HOME");
		return "home";
		
	}

	@RequestMapping(value = "/pilot/add", method = RequestMethod.GET)
	private String add(Model model) {
		
		model.addAttribute("navigation", "ADD PILOT");
		model.addAttribute("pilot", new PilotModel());
		
		return "addPilot";
	}

	@RequestMapping(value = "/pilot/add", method = RequestMethod.POST)
	private String addPilotSubmit(@ModelAttribute PilotModel pilot) {
		pilotService.addPilot(pilot);
		
		return "add";
	}

	@RequestMapping(value = "/pilot/view/{licenseNumber}", method = RequestMethod.GET)
	private String viewPilot(@PathVariable(value = "licenseNumber") String licenseNumber, Model model) {
		PilotModel pilot = pilotService.getPilotDetailByLicenseNumber(licenseNumber);
		
		model.addAttribute("navigation", "PILOT");
		model.addAttribute("pilot", pilot);
		model.addAttribute("pilotFlight", pilot.getPilotFlight());
		
		return "view-pilot";
	}

	@RequestMapping("/pilot/view")
	public String searchPilot(@RequestParam(value = "licenseNumber") String licenseNumber, Model model) {
		PilotModel pilot = pilotService.getPilotDetailByLicenseNumber(licenseNumber);
		
		model.addAttribute("navigation", "SEARCH RESULT");
		model.addAttribute("pilot", pilot);
		
		return "view-pilot";
	}

	
	@RequestMapping(value = "/pilot/delete/{licenseNumber}", method = RequestMethod.GET)
	private String deletePilot(@PathVariable(value = "licenseNumber") String licenseNumber, Model model) {
		pilotService.deletePilot(licenseNumber);
		return "delete-pilot";
	}

	

	@RequestMapping(value = "/pilot/update/{licenseNumber}", method = RequestMethod.POST)
	private String updatePilotSubmit(@ModelAttribute PilotModel updatedPilot,@PathVariable(value = "licenseNumber") String licenseNumber, Model model) {
		pilotService.updatePilot(licenseNumber, updatedPilot);
		return "updated";
	}
	
	@RequestMapping(value = "/pilot/update/{licenseNumber}", method = RequestMethod.GET)
	private String updatePilot(@PathVariable(value = "licenseNumber") String licenseNumber, Model model) {
		PilotModel pilot = pilotService.getPilotDetailByLicenseNumber(licenseNumber);
		
		model.addAttribute("navigation", "UPDATE PILOT");
		model.addAttribute("pilot", pilot);
		model.addAttribute("updatedPilot", new PilotModel());
		
		return "update-pilot";
	}

}