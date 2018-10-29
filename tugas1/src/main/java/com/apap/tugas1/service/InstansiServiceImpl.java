package com.apap.tugas1.service;

import com.apap.tugas1.model.InstansiModel;
import com.apap.tugas1.repository.InstansiDb;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class InstansiServiceImpl implements InstansiService{
	@Autowired
	private InstansiDb instansiDb;
	
	@Override
	public List<InstansiModel> getAllInstansi() {
		return instansiDb.findAll();
	}
	
	@Override
	public InstansiModel getInstansiByID(Long id) {
		return instansiDb.getOne(id);
	}
}
