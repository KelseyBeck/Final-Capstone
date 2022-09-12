package com.techelevator.dao;

import com.techelevator.model.InviteLocation;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class JdbcInviteLocationDao implements InviteLocationDao{

    private JdbcTemplate jdbcTemplate;

    public JdbcInviteLocationDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public boolean createInviteLocation(InviteLocation inviteLocation) {
        return false;
    }

    @Override
    public void updateInviteLocation(InviteLocation inviteLocation) {

    }

    //the database has a delete cascade set up on invite_id so that when an invite_id
    // gets deleted the all assocaited invite-location data also gets erased,but just in case this exists
    @Override
    public void deleteInviteLocation(int placeId,int inviteId) {
        String sql = "DELETE FROM invite_location WHERE place_id = ? and invite_id = ?;";
        jdbcTemplate.update(sql, placeId, inviteId);
        System.out.println("Invite Location Deleted");
    }


    //also cant imagine why we would need this, but just in case
    @Override
    public InviteLocation getOneLocationAssociatedWithInviteId(int placeId, int inviteId) throws Exception {

        String sql = "SELECT invite_id, place_id, no_votes, yes_votes FROM invite_location WHERE place_id =? and invite_id = ? ;";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, inviteId);
        if (results.next()) {
            return mapRowToInviteLocation(results);
        } else {
            throw new Exception("That locationId was not found with inviteId " + inviteId + ".");
        }
    }

    @Override
    public List<InviteLocation> findLocationsAssociatedWithInviteId(int inviteId) throws Exception {
        List<InviteLocation> locations = new ArrayList<>();
        String sql = "SELECT invite_id, sender_id, appointment, place_ids FROM invite_location WHERE invite_id = ? ;";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, inviteId);
        while (results.next()) {
            InviteLocation location = mapRowToInviteLocation(results);
            locations.add(location);
        }
        if (locations.size() == 0) {
            throw new Exception("Invalid invite id : " + inviteId);
        }
        return locations;
    }

    private InviteLocation mapRowToInviteLocation(SqlRowSet rs) {
        InviteLocation inviteLocation = new InviteLocation();
        inviteLocation.setInviteId(rs.getInt("invite_id"));
        inviteLocation.setPlaceId(rs.getString("place_id"));
        inviteLocation.setNoVote(rs.getInt("no_vote"));
        inviteLocation.setYesVote(rs.getInt("yes_vote"));
        return inviteLocation ;
    }

}