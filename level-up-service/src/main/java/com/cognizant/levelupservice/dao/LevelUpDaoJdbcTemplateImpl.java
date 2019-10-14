package com.cognizant.levelupservice.dao;

import com.cognizant.levelupservice.model.LevelUp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class LevelUpDaoJdbcTemplateImpl implements LevelUpDao {

    private JdbcTemplate jdbcTemplate;

    // Prepared statement strings
    private static final String INSERT_LEVEL_UP_SQL =
            "insert into level_up (customer_id, points, member_date) values (?, ?, ?)";

    private static final String SELECT_LEVEL_UP_SQL =
            "select * from level_up where level_up_id = ?";

    private static final String SELECT_ALL_LEVEL_UPS_SQL =
            "select * from level_up";

    private static final String DELETE_LEVEL_UP_SQL =
            "delete from level_up where level_up_id = ?";

    private static final String UPDATE_LEVEL_UP_SQL =
            "update level_up set customer_id = ?, points = ?, member_date = ? where level_up_id = ?";

    private static final String SELECT_LEVEL_UP_BY_CUSTOMER_ID_SQL =
            "select * from level_up where customer_id = ?";


    @Autowired
    public LevelUpDaoJdbcTemplateImpl(JdbcTemplate jdbcTemplate) {

        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public LevelUp getLevelUp(int id) {

        try {

            return jdbcTemplate.queryForObject(SELECT_LEVEL_UP_SQL, this::mapRowToLevelUp, id);

        } catch (EmptyResultDataAccessException e) {
            // if nothing is returned just catch the exception and return null
            return null;
        }
    }

    @Override
    @Transactional
    public LevelUp addLevelUp(LevelUp levelUp) {

        jdbcTemplate.update(INSERT_LEVEL_UP_SQL,
                levelUp.getCustomerId(),
                levelUp.getPoints(),
                levelUp.getMemberDate());

        int id = jdbcTemplate.queryForObject("select last_insert_id()", Integer.class);

        levelUp.setLevelUpId(id);

        return levelUp;
    }

    @Override
    public List<LevelUp> getAllLevelUps() {

        return jdbcTemplate.query(SELECT_ALL_LEVEL_UPS_SQL, this::mapRowToLevelUp);
    }

    @Override
    public void updateLevelUp(LevelUp levelUp, int id) {

        jdbcTemplate.update(UPDATE_LEVEL_UP_SQL,
                levelUp.getCustomerId(),
                levelUp.getPoints(),
                levelUp.getMemberDate(),
                levelUp.getLevelUpId());

    }

    @Override
    public void deleteLevelUp(int id) {

        jdbcTemplate.update(DELETE_LEVEL_UP_SQL, id);

    }

    @Override
    public LevelUp getLevelUpByCustomerId(int customerId) {
        try {

            return jdbcTemplate.queryForObject(SELECT_LEVEL_UP_BY_CUSTOMER_ID_SQL, this::mapRowToLevelUp, customerId);

        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    private LevelUp mapRowToLevelUp(ResultSet rs, int rowNum) throws SQLException {

        LevelUp levelUp = new LevelUp();
        levelUp.setLevelUpId(rs.getInt("level_up_id"));
        levelUp.setCustomerId(rs.getInt("customer_id"));
        levelUp.setPoints(rs.getInt("points"));
        levelUp.setMemberDate(rs.getDate("member_date").toLocalDate());

        return levelUp;

    }

}
