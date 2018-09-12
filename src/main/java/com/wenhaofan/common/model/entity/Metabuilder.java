package com.wenhaofan.common.model.entity;

import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import com.jfinal.plugin.activerecord.generator.MetaBuilder;

public class Metabuilder extends MetaBuilder {

	@SuppressWarnings({ "unchecked", "rawtypes", "serial" })
	private List<String> tables=new ArrayList(){{
		add("config");
	}};
	
	public Metabuilder(DataSource dataSource) {
		super(dataSource);
		// TODO Auto-generated constructor stub
	}
	 @Override
	    protected boolean isSkipTable(String tableName) {
	        return !tables.contains(tableName);
	    }
}
