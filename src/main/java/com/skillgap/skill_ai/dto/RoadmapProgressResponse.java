package com.skillgap.skill_ai.dto;

public class RoadmapProgressResponse {

    private long totalTasks;
    private long completedTasks;
    private long pendingTasks;
    private double progressPercentage;

    public RoadmapProgressResponse(long totalTasks,
                                   long completedTasks,
                                   long pendingTasks,
                                   double progressPercentage) {
        this.totalTasks = totalTasks;
        this.completedTasks = completedTasks;
        this.pendingTasks = pendingTasks;
        this.progressPercentage = progressPercentage;
    }

    public long getTotalTasks() {
        return totalTasks;
    }

    public long getCompletedTasks() {
        return completedTasks;
    }

    public long getPendingTasks() {
        return pendingTasks;
    }

    public double getProgressPercentage() {
        return progressPercentage;
    }
}