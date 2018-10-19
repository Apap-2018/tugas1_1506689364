package com.apap.tugas1.controller;

import com.apap.tugas1.model.JabatanModel;
import com.apap.tugas1.service.JabatanService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * JabatanController
 * @author ratna
 *
 */
@Controller
public class JabatanController {
	@Autowired
	private JabatanService jabatanService;
	
	/**
	 * Tambah Jabatan
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/jabatan/tambah", method = RequestMethod.GET)
	private String addJabatan(Model model) {
		model.addAttribute("jabatan", new JabatanModel());
		
		return "addJabatan";
	}
	
	/**
	 * Tambah Jabatan when Submit
	 * @param jabatan
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/jabatan/tambah", method = RequestMethod.POST)
	private String addJabatanSubmit(@ModelAttribute JabatanModel jabatan, Model model) {
		jabatanService.addJabatan(jabatan);
		
		return "addJabatanSubmit";
	}
	
	/**
	 * Lihat Daftar Semua Jabatan Pegawai
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/jabatan/viewAll", method = RequestMethod.GET)
	private String viewAllJabatan(Model model) {
		List<JabatanModel> listJabatan = jabatanService.getAllJabatan();
		model.addAttribute("listJabatan", listJabatan);
		
		return "viewAllJabatan";
	}
	
	/**
	 * Lihat Jabatan
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/jabatan/view", method = RequestMethod.GET)
	private String viewJabatan(@RequestParam("id") Long id, Model model) {
		JabatanModel jabatan = jabatanService.getJabatanDetailById(id);
		
		if(jabatan != null) {
			model.addAttribute("jabatan", jabatan);
			
			return "viewJabatan";
			
		} else {
			return "not-found";
		}
		
	}
	
	/**
	 * Ubah Jabatan
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/jabatan/ubah", method = RequestMethod.GET)
	private String updateJabatan(@RequestParam("id") Long id, Model model) {
		JabatanModel jabatan = jabatanService.getJabatanDetailById(id);
		
		if(jabatan != null) {
			model.addAttribute("jabatan", jabatan);
			
			return "updateJabatan";
			
		} else {
			return "not-found";
			
		}
		
	}
	
	/**
	 * Ubah Jabatan when Submit
	 * @param jabatan
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/jabatan/ubah", method = RequestMethod.POST)
	private String updateJabatanSubmit(@ModelAttribute JabatanModel jabatan, Model model) {
		jabatan.setId(jabatan.getId());
		jabatanService.updateJabatan(jabatan);
		
		return "updateJabatanSubmit";
	}
	
	/**
	 * Hapus Jabatan
	 * @param id
	 * @param jabatan
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/jabatan/hapus", method = RequestMethod.POST)
	private String deleteJabatan(@RequestParam("id") Long id, @ModelAttribute JabatanModel jabatan, Model model) {
		jabatanService.deleteJabatan(id);
		
		return "deleteJabatan";
	}
	
	
}
