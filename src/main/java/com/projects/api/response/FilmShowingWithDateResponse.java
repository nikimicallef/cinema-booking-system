package com.projects.api.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.projects.entities.FilmShowingsEntity.FilmShowing;

/**
 * Shows the {@link FilmShowing#id} and {@link FilmShowing#dateTimeStr} for a {@link FilmShowing}
 */
@JsonPropertyOrder({"showing_id", "date_time"})
public class FilmShowingWithDateResponse {
    @JsonProperty("showing_id")
    private Long showingId;
    @JsonProperty("date_time")
    private String dateTime;

    public FilmShowingWithDateResponse(final Long showingId, final String dateTime) {
        this.showingId = showingId;
        this.dateTime = dateTime;
    }

    public Long getShowingId() {
        return showingId;
    }

    public String getDateTime() {
        return dateTime;
    }
}