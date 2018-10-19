package com.apap.tugas1.service;

import com.apap.tugas1.repository.PegawaiDb;
import com.apap.tugas1.model.PegawaiModel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class PegawaiServiceImpl implements PegawaiService{
	
	@Autowired
	private PegawaiDb pegawaiDb;
	
	@Override
	public void addPegawai(PegawaiModel pegawai) {
		pegawaiDb.save(pegawai);
		
	}
	
	@Override
	public void updatePegawai(PegawaiModel pegawai) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
    public PegawaiModel getPegawaiDetailByNIP(String nip){
        return  pegawaiDb.findByNip(nip);
	}

	

	
}
