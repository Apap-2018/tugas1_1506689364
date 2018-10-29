package com.apap.tugas1.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.apap.tugas1.model.PegawaiModel;
import com.apap.tugas1.model.JabatanPegawaiModel;
import com.apap.tugas1.repository.JabatanPegawaiDb;


@Service
@Transactional
public class JabatanPegawaiServiceImpl implements JabatanPegawaiService {
	@Autowired
	private JabatanPegawaiDb jabatanPegawaiDb;
	
	@Override
	public void setPegawaiJabatan(PegawaiModel pegawai) {
		for(JabatanPegawaiModel pegawaiJabatan : pegawai.getJabatanPegawai()) {
			pegawaiJabatan.setPegawai(pegawai);
			
		}
	}
}
