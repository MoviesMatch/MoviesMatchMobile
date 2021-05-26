package com.example.moviesmatch.models;

import java.util.ArrayList;
import java.util.Comparator;

public class Group implements Comparator<Group>, Comparable<Group> {
    String groupId, groupJoinCode, groupName;
    ArrayList<String> moviesGroup, members;

    public Group(String groupId, String groupJoinCode, String groupName) {
        this.groupId = groupId;
        this.groupJoinCode = groupJoinCode;
        this.groupName = groupName;
    }

    public Group(String groupId, String groupJoinCode, String groupName, ArrayList<String> moviesGroup, ArrayList<String> members) {
        this.groupId = groupId;
        this.groupJoinCode = groupJoinCode;
        this.groupName = groupName;
        this.moviesGroup = moviesGroup;
        this.members = members;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getGroupJoinCode() {
        return groupJoinCode;
    }

    public void setGroupJoinCode(String groupJoinCode) {
        this.groupJoinCode = groupJoinCode;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public ArrayList<String> getMoviesGroup() {
        return moviesGroup;
    }

    public void setMoviesGroup(ArrayList<String> moviesGroup) {
        this.moviesGroup = moviesGroup;
    }

    public ArrayList<String> getMembers() {
        return members;
    }

    public void setMembers(ArrayList<String> members) {
        this.members = members;
    }

    @Override
    public int compareTo(Group o) {
        return this.groupName.compareTo(o.getGroupName());
    }

    @Override
    public int compare(Group o1, Group o2) {
        return o1.getGroupName().compareTo(o2.getGroupName());
    }
}
