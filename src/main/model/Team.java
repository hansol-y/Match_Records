package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;

//Creates a team containing number of matches
public class Team implements Writable {

    //fields
    private final String teamName;
    private MatchList matchList;
    private final ArrayList<String> matchDateList = new ArrayList<>();
    private Match match;

    //constructor
    //EFFECTS: create a new team with given name
    public Team(String teamName) {
        this.teamName = teamName;
        matchList = new MatchList(teamName);
        match = null;
    }

    //methods
    //REQUIREMENTS: result should be one of 'w', 'l' and 'n'
    //MODIFIES: this
    //EFFECTS: add Match to the selected date
    public void addMatch(String date, double time, String oppTeam, boolean booking, String result, String impression) {

        match = new Match(date);

        match.setTime(time);
        match.setOpposingTeam(oppTeam);
        match.setBooking(booking);
        match.setResult(result);
        match.setImpression(impression);

        matchList.addMatch(match);
        matchDateList.add(date);
    }

    //MODIFIES: this
    //EFFECTS: add match to matchList and matchDataList
    public void matchToList() {
        matchList.addMatch(match);
        setMatchDateList(match.getDate());
    }

    //EFFECTS: bring recorded match with a selected date
    public int recordedMatch(String date) {
        if (matchDateList.contains(date)) {
            for (Match i : matchList.getMatchList()) {
                if (i.getDate().equals(date)) {
                    return matchList.getMatchList().indexOf(i);
                }
            }
        }
        return -1;
    }

    //EFFECTS: find all matches that booking is true, add them to a string and return the string
    public String allBookedMatch() {
        StringBuilder string = new StringBuilder();
        for (Match i : matchList.getMatchList()) {
            if (i.isBooking()) {
                string.append(i.getDate()).append("\n");
            }
        }
        return string.toString();
    }

    //EFFECTS: return name of the team
    public String getTeamName() {
        return teamName;
    }

    //EFFECTS: return the list of matches
    public ArrayList<Match> getMatchList() {
        return matchList.getMatchList();
    }

    //EFFECTS: return the list of match days
    public ArrayList<String> getMatchDateList() {
        return matchDateList;
    }

    //EFFECTS: return the match
    public Match getMatch() {
        return match;
    }

    //MODIFIES: this
    //EFFECTS: add given date to matchDateList
    public void setMatchDateList(String date) {
        matchDateList.add(date);
    }

    @Override
    //EFFECTS: returns team as a JSON object
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("teamName", teamName);
        json.put("matchList", matchesToJson());
        return json;
    }

    //EFFECTS: returns things in this workroom as a JSON array
    private JSONArray matchesToJson() {
        JSONArray jsonArray = new JSONArray();
        for (Match match : matchList.getMatchList()) {
            jsonArray.put(match.toJson());
        }

        return jsonArray;
    }
}
