package com.step.webServer.domain;

import java.time.LocalDateTime;

public class Followupplan  implements Domain{
    private Integer followupplanId;
    private LocalDateTime followupplanDatetime;
    private Integer confirmed;
    private Integer followupId;

    public Integer getFollowupplanId() {
        return followupplanId;
    }

    public void setFollowupplanId(Integer followupplanId) {
        this.followupplanId = followupplanId;
    }

    public LocalDateTime getFollowupplanDatetime() {
        return followupplanDatetime;
    }

    public void setFollowupplanDatetime(LocalDateTime followupplanDatetime) {
        this.followupplanDatetime = followupplanDatetime;
    }

    public Integer getConfirmed() {
        return confirmed;
    }

    public void setConfirmed(Integer confirmed) {
        this.confirmed = confirmed;
    }

    public Integer getFollowupId() {
        return followupId;
    }

    public void setFollowupId(Integer followupId) {
        this.followupId = followupId;
    }
}
