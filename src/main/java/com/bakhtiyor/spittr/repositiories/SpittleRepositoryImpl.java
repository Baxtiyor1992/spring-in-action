package com.bakhtiyor.spittr.repositories;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.bakhtiyor.spittr.models.Spittle;

@Repository
public class SpittleRepositoryImpl implements SpittleRepository {

	private JdbcOperations jdbc;

	@Autowired
	public SpittleRepositoryImpl(JdbcOperations jdbc) {
		this.jdbc = jdbc;
	}

	public List<Spittle> findSpittles(long max, int count) {
		return jdbc.query(
			"select id, message, created_at, latitude, longitude" +
					" from Spittle" +
					" where id < ?" +
					" order by created_at desc limit 20",
			new SpittleRowMapper(),
			max
		);
	}

	private static class SpittleRowMapper implements RowMapper<Spittle> {
		
		public Spittle mapRow(ResultSet rs, int rowNum) throws SQLException {
			return new Spittle(
				rs.getLong("id"),
				rs.getString("message"), 
				rs.getDate("created_at"), 
				rs.getDouble("longitude"), 
				rs.getDouble("latitude")
			);
		}
	}
}