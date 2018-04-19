package com.zht.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zht.mapper.IndexMapper;

@Service
public class LogServiceImpl implements LogService {

	@Autowired
	IndexMapper indexMapper;
	
	@Override
	public void log(String string) {
		indexMapper.log(string);
	}

}
