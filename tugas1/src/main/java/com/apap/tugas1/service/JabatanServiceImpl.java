package com.apap.tugas1.service;

import com.apap.tugas1.model.JabatanModel;
import com.apap.tugas1.repository.JabatanDb;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional
public class JabatanServiceImpl implements JabatanService {
	
	@Autowired
	private JabatanDb jabatanDb;
	
	@Override
	public void addJabatan(JabatanModel jabatan){
		jabatanDb.save(jabatan);
	}
	
	@Override
	public void deleteJabatan(Long id) {
		jabatanDb.deleteById(id);
	}

	@Override
	public void updateJabatan(JabatanModel jabatan) {
		JabatanModel jabatanTemp = jabatanDb.getOne(jabatan.getId());
		jabatanTemp.setNama(jabatan.getNama());
		jabatanTemp.setGaji_pokok(jabatan.getGaji_pokok());;
		jabatanTemp.setDeskripsi(jabatan.getDeskripsi());
		
		
	}

	@Override
	public JabatanModel getJabatanDetailById(Long id) {
		return jabatanDb.getDetailById(id);
	}
	
	@Override
	public List<JabatanModel> getAllJabatan() {
		return jabatanDb.findAll();
	}
	
	
}
