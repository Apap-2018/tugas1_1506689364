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
import com.apap.tugas1.service.JabatanPegawaiService;


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
	
	@Autowired
	private JabatanPegawaiService jabatanPegawaiService;
	
	/**
	 * Home
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/", method= RequestMethod.GET)
	private String home(Model model) {
		List<JabatanModel> allListJabatan = jabatanService.getAllJabatan();
		model.addAttribute("allListJabatan", allListJabatan);
		List<InstansiModel> allListInstansi = instansiService.getAllInstansi();
		model.addAttribute("allListInstansi", allListInstansi);
		
		return "home";
		
	}
	
	/**
	 * Fitur 1
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
	 * Fitur 2
	 * (belum selesai)
	 * Tambah Pegawai
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/pegawai/tambah", method = RequestMethod.GET)
	private String addPegawai(Model model) {
		PegawaiModel pegawai = new PegawaiModel();
		model.addAttribute("pegawai", pegawai);
		
		List<ProvinsiModel> listProvinsi =  provinsiService.getAllProvinsi();
		model.addAttribute("listProvinsi", listProvinsi);
		
		List<InstansiModel> listInstansi = instansiService.getAllInstansi();
		model.addAttribute("listInstansi", listInstansi);
		
		List<JabatanModel> listJabatan =  jabatanService.getAllJabatan();
		model.addAttribute("listJabatan", listJabatan);
		

		
		return "addPegawai";
	}
	
	/**
	 * Tambah Pegawai when Submit
	 * (belum set NIP pegawai)
	 * @param pegawai
	 * @return
	 */
	@RequestMapping(value = "/pegawai/tambah", method = RequestMethod.POST)
	private String addPegawaiSubmit(@ModelAttribute PegawaiModel pegawai, Model model) {
		jabatanPegawaiService.setPegawaiJabatan(pegawai);
		
		pegawaiService.addPegawai(pegawai);
		model.addAttribute("pegawai", pegawai);
		return "addPegawaiSubmit";
	}
	
	/**
	 * Fitur 4
	 * (belum selesai)
	 * Mencari Pegawai Berdasarkan Provinsi, Instansi, Jabatan
	 * @param idProvinsi
	 * @param idInstansi
	 * @param idJabatan
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/pegawai/cari", method = RequestMethod.GET)
	public String searchPegawai(@RequestParam(value = "idProvinsi", required = false) String idProvinsi,
								@RequestParam(value = "idInstansi", required = false) String idInstansi,
								@RequestParam(value = "idJabatan", required = false) String idJabatan,
								Model model) {
	
	List<ProvinsiModel> listProvinsi = provinsiService.getAllProvinsi();
	List<InstansiModel> listInstansi = instansiService.getAllInstansi();
	List<JabatanModel> listJabatan = jabatanService.getAllJabatan();
	
	model.addAttribute("listProvinsi", listProvinsi);
	model.addAttribute("listInstansi", listInstansi);
	model.addAttribute("listJabatan", listJabatan);
	
	
	
		return "searchPegawai";
	}
	
	/**
	 * Fitur 10
	 * Lihat Pegawai Termuda dan Tertua
	 * @param idInstansi
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/pegawai/termuda-tertua", method = RequestMethod.GET)
	private String youngestOldestPegawai(@RequestParam("idInstansi") Long idInstansi, Model model) {
		InstansiModel instansi = instansiService.getInstansiByID(idInstansi);
		
		if(instansi != null) {
			PegawaiModel oldestPegawai = new PegawaiModel();
			PegawaiModel youngestPegawai = new PegawaiModel();
			for(PegawaiModel pegawai : instansi.getInstansiPegawai()) {
				if(oldestPegawai.getTanggal_lahir() == null || pegawai.getTanggal_lahir().compareTo(oldestPegawai.getTanggal_lahir()) < 0  ) {
					oldestPegawai = pegawai;
					model.addAttribute("oldestPegawai", oldestPegawai);
					
				}
				else if(youngestPegawai.getTanggal_lahir() == null || pegawai.getTanggal_lahir().compareTo(youngestPegawai.getTanggal_lahir()) > 0) {
					youngestPegawai = pegawai;
					model.addAttribute("youngestPegawai", youngestPegawai);
				}
			}		
			return "youngestOldestPegawai";
		} else {
			return "not-found";
		}
	}	
	
	

}
