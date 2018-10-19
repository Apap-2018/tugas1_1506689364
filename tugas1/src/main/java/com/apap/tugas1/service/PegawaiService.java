package com.apap.tugas1.service;

import com.apap.tugas1.model.PegawaiModel;

public interface PegawaiService {
	void addPegawai (PegawaiModel pegawai);
	void updatePegawai (PegawaiModel pegawai);
	PegawaiModel getPegawaiDetailByNIP(String nip);
}
