package com.apap.tugas1.controller;

import java.util.List;

import com.apap.tugas1.model.PegawaiModel;
import com.apap.tugas1.model.JabatanModel;
import com.apap.tugas1.model.ProvinsiModel;
import com.apap.tugas1.model.InstansiModel;
import com.apap.tugas1.service.PegawaiService;
import com.apap.tugas1.service.JabatanService;
import com.apap.tugas1.service.ProvinsiService;
import com.apap.tugas1.service.InstansiService;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * PegawaiController
 * @author ratna
 *
 */
@Controller
public class PegawaiController {
	@Autowired
	private PegawaiService pegawaiService;
	
	@Autowired
	private JabatanService jabatanService;
	
	@Autowired
	private ProvinsiService provinsiService;
	
	@Autowired
	private InstansiService instansiService;
	
	/**
	 * Home
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/", method= RequestMethod.GET)
	private String home(Model model) {
		List<JabatanModel> allListJabatan = jabatanService.getAllJabatan();
		model.addAttribute("allListJabatan", allListJabatan);
		
		return "home";
		
	}
	
	/**
	 * Lihat Pegawai Berdasarkan NIP
	 * @param nip
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/pegawai", method=RequestMethod.GET)
	private String viewPegawai(@RequestParam("nip") String nip, Model model) {
		PegawaiModel pegawai = pegawaiService.getPegawaiDetailByNIP(nip);		
		
		if(pegawai != null) {
			List<JabatanModel> jabatan = pegawai.getJabatan();
			int gajiPokok = (int) jabatan.get(0).getGaji_pokok();
			int gajiTemp = (int) (jabatan.get(0).getGaji_pokok()*(pegawai.getInstansi().getProvinsi().getPresentase_tunjangan()/100));
			int gaji = gajiPokok + gajiTemp;
			
			model.addAttribute("pegawai", pegawai);
			model.addAttribute("gaji", gaji);
			model.addAttribute("jabatan", jabatan);
			
			return "viewPegawai";
		} else {
			return "not-found";
		}
		
	}
	
	/**
	 * Tambah Pegawai
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/pegawai/tambah", method = RequestMethod.GET)
	private String addPegawai(Model model) {
		PegawaiModel pegawai = new PegawaiModel();
		model.addAttribute("pegawai", pegawai);
		
//		List<ProvinsiModel> listProvinsi =  provinsiService.getAllProvinsi();
//		model.addAttribute("listProvinsi", listProvinsi);
		
//		List<JabatanModel> listJabatan =  jabatanService.getAllJabatan();
//		model.addAttribute("listJabatan", listJabatan);
		
//		List<InstansiModel> listInstansi = instansiService.getAllInstansi();
//		model.addAttribute("listInstansi", listInstansi);
		
		return "addPegawai";
	}
	
	/**
	 * Tambah Pegawai when Submit
	 * @param pegawai
	 * @return
	 */
	@RequestMapping(value = "/pegawai/tambah", method = RequestMethod.POST)
	private String addPegawaiSubmit(@ModelAttribute PegawaiModel pegawai) {
		pegawaiService.addPegawai(pegawai);
		
		return "addPegawaiSubmit";
	}
	
	
	
	
	

}
