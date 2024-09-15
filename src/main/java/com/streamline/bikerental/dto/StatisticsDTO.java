package com.streamline.bikerental.dto;

public class StatisticsDTO {

    private long totalUsers;
    private long totalBikes;
    private long totalBookings;
    private long totalActiveBookings;
    private long totalPreviousBookings;

    // Getters and setters

    public long getTotalUsers() {
        return totalUsers;
    }

    public void setTotalUsers(long totalUsers) {
        this.totalUsers = totalUsers;
    }

    public long getTotalBikes() {
        return totalBikes;
    }

    public void setTotalBikes(long totalBikes) {
        this.totalBikes = totalBikes;
    }

    public long getTotalBookings() {
        return totalBookings;
    }

    public void setTotalBookings(long totalBookings) {
        this.totalBookings = totalBookings;
    }

    public long getTotalActiveBookings() {
        return totalActiveBookings;
    }

    public void setTotalActiveBookings(long totalActiveBookings) {
        this.totalActiveBookings = totalActiveBookings;
    }

    public long getTotalPreviousBookings() {
        return totalPreviousBookings;
    }

    public void setTotalPreviousBookings(long totalPreviousBookings) {
        this.totalPreviousBookings = totalPreviousBookings;
    }
}
